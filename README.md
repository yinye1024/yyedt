
# erlang dev tools
erlang 开发工具集，提供日常erlang开发的常用工具，视使用情况会陆续添加新的工具

### 使用
   把 rel/yyedt-1.0-SNAPSHOT.jar 本地安装到 IntelliJ IDEA 。
   或者自己build，是 IntelliJ IDEA 标准的 gradle 插件项目。


### 有哪些功能
1.目录/文件/内容替换
     
   使用:模版目录右键，选择 erlang开发工具集->目录/文件/内容替换,按导航生成对应新目录/文件。

   新功能开发的时候，可以通过模版或者相类似的已经开发好的功能模块，通过替换的方法生成新功能的模版，然后再进行业务开发。
   保持代码结构的一致性。

2.生成 get set 方法

   使用:需要生成get set 方法的map字段，同时按 Ctrl+Alt+2 按导航生成对应代码。

3.检查重复的模块名

   使用:目录右键，选择 erlang开发工具集-> 检查重复的模块名

4.检查模块依赖

   使用:目标目录右键，选择 erlang开发工具集-> 检查模块依赖,选择源目录和目标目录，
   会列出源目录被目标目录下模块引用的模块列表（源目录有哪些模块是被目标目录下的文件引用过），
   以及源目录下模块，引用过的目标目录下的模块列表（源目录文件引用了哪些目标目录的文件）。

5.检查无引用模块方法

   使用:目标目录右键，选择 erlang开发工具集-> 检查无引用模块方法,列出目标目录下无引用的模块方法。
 