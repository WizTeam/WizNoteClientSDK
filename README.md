# 为知笔记客户端SDK
利用为知笔记客户端SDK，可以快速的将为知笔记嵌入到您自己的应用中，适合于拥有自己手机客户端的中大型企业。

## ios客户端

为知笔记提供了静态库文件，可以直接嵌入到ios客户的应用中。由于为知笔记使用了一些第三方库，而企业客户的应用中，也有可能同时使用了这些第三方，并且版本不同，因此我们建议将为知笔记sdk封装为一个framework来调用。

### 包含的文件
1. libwiznote.a 所有代码静态库
2. wiznote.h 为知笔记SDK头文件
3. wiznote.bundle 所需要的资源

### 使用方法

#### 作为静态库直接链接
1. 将sdk里面的文件三个文件（wiznote.bundle作为一个文件）加入到您的项目里面。
2. 在对应的target里面加入libwiznote.a 和 wiznote.bundle
3. 在需要显示笔记的地方，调用相关功能。具体代码请参考例子。
4. 在项目的target的build settings里面，找到Other Linker Flags，加入`-all_load -ObjC -lz`三个选项。
5. Build Phases里面，找到Link Binary with librarys，添加libc++.tbd（点击+，可以在出现的对话框中搜索libc++）。
6. 编译应用即可。


#### 封装为framewrok使用
1. 点击Xcode菜单，File -> New -> Target, 在对话框中选择Cocoa Touch Framework，输入一个framework名称，例如WizNoteFramework。注意对话框里面选择将framework嵌入到您自己的项目中（target）。
2. 按照作为静态库方式操作，将为知sdk添加到framework里面使用。
3. 参考例子中的WizNoteFramework.h 和WizNoteFramework.m文件，在framework里面增加相关方法，并进行导出。
4. 参考例子中的方式，设置并启动笔记功能。

### 图片修改
1. 找到wiznote.bundle文件，直接修改里面的图片，然后重新编译即可。

### 文字修改
1. 找到wiznote.bundle文件，找到对应语言的Localizable.strings，直接修改，然后重新编译即可。


