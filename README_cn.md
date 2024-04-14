[English](https://github.com/smbServer/Pro2Json/README.md) | **中文**
# Pro2Json
## 一个 ProGuard 到 json 是没用的，方便我解析混乱表，说实话是懒惰......
## 主要开发者:
- Frish2021

## 开发时间:
> 2024/4/14 1:26

## 如何导入依赖项
### 步骤 1 -- 设置 Maven 仓库的源代码
```gradle
maven {
  name 'sunmoonbay'
  url 'https://smbServer.github.io/'
}
```

### 步骤 2 -- 导入依赖关系
``` gradle
implementation group: 'xyz.frish2021', name: 'Pro2Json', version: '1.0.0'
```

## 如何使用此依赖项

### 步骤 1 -- 创建两个变量，一个用于导出文件，一个用于输入文件
``` java
File inFile = new File(System.getProperty("user.dir"), "mapping.txt"); // input ProGuard mapping
File outFile = new File(System.getProperty("user.dir"), "mapping.json"); // output json mapping
```

### 步骤 2 -- 创建实例
```java
Pro2Json pro2Json = new Pro2Json(inFile, outFile);
```

### 步骤 3  -- 调用主方法
```java
pro2Json.genJsonMapping(); // Invoke Method
```
