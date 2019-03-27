基于http的RPC框架

使用方法，用Maven打包成jar包，引入项目中

（

jar文件夹下面是我打好的jar包，可以直接引入项目中使用。

或者下载源码通过Maven install安装到本地Maven仓库,然后再pom文件中配置

）



    <dependency>
      <groupId>com.biubiu.boss</groupId>
      <artifactId>rpc-sdk</artifactId>
      <version>1.0.0.beta</version>
    </dependency>


需要服务部署在同一个网关内


在方法中使用的方法为：

//物参数get 方法

ApiResult apiResult = Rpc.httpCall("/user/list").get();

//有参数get方法

ApiResult apiResult = Rpc.httpCall("/user/list").addUrl("status","0").addUrl("sex","1").get();

//post方法

ApiResult apiResult = Rpc.httpCall(/user/update).addUrl("userId", "1").addUrl("status", "0").post();

其它的方法是用类似。
