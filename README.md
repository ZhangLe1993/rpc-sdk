基于http的RPC框架

使用方法，用Maven打包成jar包，引入项目中

在方法中使用的方法为：

//物参数get 方法

ApiResult apiResult = Rpc.httpCall("/user/list").get();

//有参数get方法

ApiResult apiResult = Rpc.httpCall("/user/list").addUrl("status","0").addUrl("sex","1").get();

//post方法

ApiResult apiResult = Rpc.httpCall(/user/update).addUrl("userId", "1").addUrl("status", "0").post();

其它的方法是用类似。
