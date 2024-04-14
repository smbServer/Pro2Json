**English** | [中文](https://github.com/smbServer/Pro2Json/blob/main/README_cn.md)
# Pro2Json
## A ProGuard to json is useless, is convenient for me to parse the confusion table, to be honest is lazy...

## Main Developers:
- Frish2021

## Development time:
> 2024/4/14 1:26

## How to import dependencies
### Step 1. -- Set the source of the Maven repository
```gradle
maven {
  name 'sunmoonbay'
  url 'https://smbServer.github.io/'
}
```

### Step 2. -- Import dependencies
``` gradle
implementation group: 'xyz.frish2021', name: 'Pro2Json', version: '1.0.0'
```

## How to use this dependencies

## Step 1. -- Create two variables, one for the export file and one for the input file.
``` java
File inFile = new File(System.getProperty("user.dir"), "mapping.txt"); // input ProGuard mapping
File outFile = new File(System.getProperty("user.dir"), "mapping.json"); // output json mapping
```

## Step 2. -- Create an instance
```java
Pro2Json pro2Json = new Pro2Json(inFile, outFile);
```

## Step 3. -- Invoke the primary method
```java
pro2Json.genJsonMapping(); // Invoke Method
```
