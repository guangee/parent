package com.coding;

import com.coding.common.Container;
import com.coding.proxy.ProxyProperties;
import com.coding.handlers.ClientChannelHandler;
import com.coding.handlers.RealServerChannelHandler;
import com.coding.listener.ChannelStatusListener;
import com.coding.proto.IdleCheckHandler;
import com.coding.proto.ProxyMessage;
import com.coding.proto.ProxyMessageDecoder;
import com.coding.proto.ProxyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

@Slf4j
@Component
public class ProxyClientContainer implements Container, ChannelStatusListener {


    private static final int MAX_FRAME_LENGTH = 1024 * 1024;

    private static final int LENGTH_FIELD_OFFSET = 0;

    private static final int LENGTH_FIELD_LENGTH = 4;

    private static final int INITIAL_BYTES_TO_STRIP = 0;

    private static final int LENGTH_ADJUSTMENT = 0;

    private final NioEventLoopGroup workerGroup;

    private final Bootstrap bootstrap;

    private final Bootstrap realServerBootstrap;
    private final ProxyProperties config;

    private SSLContext sslContext;

    private long sleepTimeMill = 1000;

    public ProxyClientContainer(ProxyProperties config) {
        workerGroup = new NioEventLoopGroup();
        realServerBootstrap = new Bootstrap();
        realServerBootstrap.group(workerGroup);
        realServerBootstrap.channel(NioSocketChannel.class);
        realServerBootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new RealServerChannelHandler());
            }
        });

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            public void initChannel(SocketChannel ch) {
                if (config.getSslEnable()) {
                    if (sslContext == null) {
                        sslContext = SslContextCreator.createSSLContext(config);
                    }

                    ch.pipeline().addLast(createSslHandler(sslContext));
                }
                ch.pipeline().addLast(new ProxyMessageDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
                ch.pipeline().addLast(new ProxyMessageEncoder());
                ch.pipeline().addLast(new IdleCheckHandler(IdleCheckHandler.READ_IDLE_TIME, IdleCheckHandler.WRITE_IDLE_TIME - 10, 0));
                ch.pipeline().addLast(new ClientChannelHandler(realServerBootstrap, bootstrap, ProxyClientContainer.this, config));
            }
        });
        this.config = config;
    }

    @Override
    public void start() {
        connectProxyServer();
    }

    private ChannelHandler createSslHandler(SSLContext sslContext) {
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setUseClientMode(true);
        return new SslHandler(sslEngine);
    }

    private void connectProxyServer() {

        bootstrap.connect(config.getServerHost(), config.getServerPort()).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {

                // 连接成功，向服务器发送客户端认证信息（clientKey）
                ClientChannelMannager.setCmdChannel(future.channel());
                ProxyMessage proxyMessage = new ProxyMessage();
                proxyMessage.setType(ProxyMessage.C_TYPE_AUTH);
                proxyMessage.setUri(config.getClientKey());
                future.channel().writeAndFlush(proxyMessage);
                sleepTimeMill = 1000;
                log.info("connect proxy server success, {}", future.channel());
            } else {
                log.warn("connect proxy server failed", future.cause());

                // 连接失败，发起重连
                reconnectWait();
                connectProxyServer();
            }
        });
    }

    @Override
    public void stop() {
        workerGroup.shutdownGracefully();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        reconnectWait();
        connectProxyServer();
    }

    private void reconnectWait() {
        try {
            if (sleepTimeMill > 60000) {
                sleepTimeMill = 1000;
            }

            synchronized (this) {
                sleepTimeMill = sleepTimeMill * 2;
                wait(sleepTimeMill);
            }
        } catch (InterruptedException e) {
            log.error("reconnectWait", e);
        }
    }

//    public static void main(String[] args) {
//
//        ContainerHelper.start(Arrays.asList(new Container[] { new ProxyClientContainer() }));
//    }

}
