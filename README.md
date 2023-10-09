l 系统应实现的主要功能：

（1） 用户登录功能

（2） 用户查询书籍、搜索书籍功能、分类查询书籍功能。

（3） 用户查询订单功能

（4） 用户修改个人信息功能

（5） 用户可以发布自己想出售的二手书

l 各模块的具体功能和简单算法：

1、书籍管理模块：

![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image002.gif)     ![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image004.gif)

book_moudle模块功能：将resources中的books中的书籍(电子书、实体书)信息文件(book_1.txt)读取出书籍信息（文本），此过程由io包中的类进行完成。再将其进行转为java对象，交给bookstore包中的类进行管理，此过程由bookstore包中的类进行完成。Bookstore包中的类再用HashMap数据结构将书籍信息进行管理，这样只需要根据书名就可以取出书籍对象，具体流程为以下：

![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image006.gif)

2、订单管理模块

![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image008.gif)   ![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image010.gif)

Order_moudle模块：功能实现过程与书籍一样，orders包中的类管理所有订单资源，过程如下：

![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image012.gif)

3、用户管理模块：

 ![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image014.gif)  ![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image016.gif)

User_moudle模块：功能实现及其运作流程同上，直接给出流程图。

![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image018.gif)

4、客户端-服务端模块：

  ![img](file:///C:/Users/康熙/AppData/Local/Packages/oice_16_974fa576_32c1d314_848/AC/Temp/msohtmlclip1/01/clip_image020.gif)

​    功能实现：本系统采取TCP通信进行前后端传数据，数据就是我用字符串拼接而成的“网页”用户通过客户端与服务端通信，服务端返回数据，以上三大模块都包含在服务端里，集成所有功能，为用户服务。Client客户端，UsedBookTradingPlatform服务端，utils包中是“网页”绘制的工具。



注意：存储文件名有格式要求，可以自行添加存储文件，数据将平摊到各个文件中