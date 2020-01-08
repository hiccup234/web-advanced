//============= 会话描述 ====================
v=0 
o=- 7017624586836067756 2 IN IP4 127.0.0.1
s=-
t=0 0
...

//================ 媒体描述 =================
//================ 音频媒体 =================
/*
 * 音频使用端口 1024 收发数据
 * UDP/TLS/RTP/SAVPF 表示使用 dtls/srtp 协议对数据加密传输
 * 111、103 ... 表示本会话音频数据的 Payload Type
 */
 m=audio 1024 UDP/TLS/RTP/SAVPF 111 103 104 9 0 8 106 105 13 126 

//============== 网络描述 ==================
// 指明接收或者发送音频使用的 IP 地址，由于 WebRTC 使用 ICE 传输，这个被忽略。
c=IN IP4 0.0.0.0
// 用来设置 rtcp 地址和端口，WebRTC 不使用
a=rtcp:9 IN IP4 0.0.0.0
...

//============== 音频安全描述 ================
//ICE 协商过程中的安全验证信息
a=ice-ufrag:khLS
a=ice-pwd:cxLzteJaJBou3DspNaPsJhlQ
a=fingerprint:sha-256 FA:14:42:3B:C7:97:1B:E8:AE:0C2:71:03:05:05:16:8F:B9:C7:98:E9:60:43:4B:5B:2C:28:EE:5C:8F3:17
...

//============== 音频流媒体描述 ================
a=rtpmap:111 opus/48000/2
//minptime 代表最小打包时长是 10ms，useinbandfec=1 代表使用 opus 编码内置 fec 特性
a=fmtp:111 minptime=10;useinbandfec=1
...
a=rtpmap:103 ISAC/16000
a=rtpmap:104 ISAC/32000
a=rtpmap:9 G722/8000
...

//================= 视频媒体 =================
m=video 9 UDP/TLS/RTP/SAVPF 100 101 107 116 117 96 97 99 98
...
//================= 网络描述 =================
c=IN IP4 0.0.0.0
a=rtcp:9 IN IP4 0.0.0.0
...
//================= 视频安全描述 =================
a=ice-ufrag:khLS
a=ice-pwd:cxLzteJaJBou3DspNaPsJhlQ
a=fingerprint:sha-256 FA:14:42:3B:C7:97:1B:E8:AE:0C2:71:03:05:05:16:8F:B9:C7:98:E9:60:43:4B:5B:2C:28:EE:5C:8F3:17
...

//================ 视频流描述 ===============
a=mid:video
...
a=rtpmap:100 VP8/90000
//================ 服务质量描述 ===============
a=rtcp-fb:100 ccm fir
a=rtcp-fb:100 nack // 支持丢包重传，参考 rfc4585
a=rtcp-fb:100 nack pli
a=rtcp-fb:100 goog-remb // 支持使用 rtcp 包来控制发送方的码流
a=rtcp-fb:100 transport-cc
...
