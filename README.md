# Brotli
提取google的brotli源码中的java部分，在android中以cmake方式进行构建

1、project处的build.gradle中添加maven { url 'https://dl.bintray.com/chfdeng/maven' }   （jcenter审核中）

2、implementation 'org.zero:brotli:1.0.0'

3、压缩：Encoder.compress();
   解压：BrotliInputStream()传入输入流，然后read得到byte数组，new String(byte[])即可得到解压后的字符串
