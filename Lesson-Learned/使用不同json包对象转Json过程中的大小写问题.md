# 使用不同工具对象转JsonObject中的大小写问题

## 1. `net.sf.json-lib` 与 `com.alibaba.fastjson`的不同效果

### 1-1. 导入jar包

这里同时导入两个json工具包

```xml
<dependency>
    <groupId>net.sf.json-lib</groupId>
    <artifactId>json-lib</artifactId>
    <version>2.4</version>
</dependency>

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.45</version>
</dependency>
```

### 1-2. 对象采用驼峰命名法

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private String companyName;

}

```

#### 1-2-1. 使用`net.sf.json-lib`

```java
import net.sf.json.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user = new User(1, "zhangsan", "Male", 18, "华为");
        JSONObject jsonObject = JSONObject.fromObject(user);
        System.out.println(jsonObject.toString());
    }
}

结果：(与对象名一致，没毛病)
{"age":18,"companyName":"华为","id":1,"name":"zhangsan","sex":"Male"}
```

#### 1-2-2. 使用fastjson

```java
import com.alibaba.fastjson.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user = new User(1, "zhangsan", "Male", 18, "华为");
        String jsonString = JSONObject.toJSONString(user);
        System.out.println(jsonString);
    }

}
结果：(与对象名一致，没毛病)
{"age":18,"companyName":"华为","id":1,"name":"zhangsan","sex":"Male"}
```

### 1-3. 对象首字母大写

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer Id;

    private String Name;

    private String Sex;

    private Integer Age;

    private String CompanyName;

    private String Remark;

}
```

#### 1-3-1. 使用`net.sf.json-lib

```java
import net.sf.json.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null);
        User user2 = new User(2, "Lisi", "Female", null, "小米", "");
        JSONObject jsonObject1 = JSONObject.fromObject(user1);
        JSONObject jsonObject2 = JSONObject.fromObject(user2);
        System.out.println("user1  " + jsonObject1.toString());
        System.out.println("user2  " + jsonObject2.toString());
    }

}

结果: 首字母变成小写,保留了值为null的节点（0和""）
user1  {"age":18,"companyName":"华为","id":1,"name":"zhangsan","remark":"","sex":"Male"}
user2  {"age":0,"companyName":"小米","id":2,"name":"Lisi","remark":"","sex":"Female"}
```

#### 1-3-2. 使用fastjson

```java
import com.alibaba.fastjson.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null);
        User user2 = new User(2, "Lisi", "Female", null, "小米", "");
        String jsonString1 = JSONObject.toJSONString(user1);
        String jsonString2 = JSONObject.toJSONString(user2);
        System.out.println("user1  " + jsonString1);
        System.out.println("user2  " + jsonString2);
    }

}

结果：首字母自动变成小写,自动去掉了值为null的节点(为""的仍然保留)
user1  {"age":18,"companyName":"华为","id":1,"name":"zhangsan","sex":"Male"}
user2  {"companyName":"小米","id":2,"name":"Lisi","remark":"","sex":"Female"}
```

如果需要一定按指定的字段名，可使用@JSONField

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JSONField(name = "Id")
    private Integer Id;

    @JSONField(name = "Name")
    private String Name;

    @JSONField(name = "Sex")
    private String Sex;

    @JSONField(name = "Age")
    private Integer Age;

    @JSONField(name = "CompanyName")
    private String CompanyName;

    @JSONField(name = "Remark")
    private String Remark;

}

import com.alibaba.fastjson.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null);
        User user2 = new User(2, "Lisi", "Female", null, "小米", "");
        String jsonString1 = JSONObject.toJSONString(user1);
        String jsonString2 = JSONObject.toJSONString(user2);
        System.out.println("user1  " + jsonString1);
        System.out.println("user2  " + jsonString2);
    }

}

结果: 按JSONField指定的name转json，自动去掉了值为null的节点(为""的仍然保留)
user1  {"Age":18,"CompanyName":"华为","Id":1,"Name":"zhangsan","Sex":"Male"}
user2  {"CompanyName":"小米","Id":2,"Name":"Lisi","Remark":"","Sex":"Female"}
```

### 1-4. 对象属性全部大写

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer ID;

    private String NAME;

    private String SEX;

    private Integer AGE;

    private String COMPANYNAME;

    private String REMARK;

}
```

#### 1-4-1. 使用`net.sf.json-lib

```java
import net.sf.json.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null);
        User user2 = new User(2, "Lisi", "Female", null, "小米", "");
        JSONObject jsonObject1 = JSONObject.fromObject(user1);
        JSONObject jsonObject2 = JSONObject.fromObject(user2);
        System.out.println("user1  " + jsonObject1.toString());
        System.out.println("user2  " + jsonObject2.toString());
    }

}

结果：首字母没有自动变成小写,保留了值为null的节点（0和""）
user1  {"AGE":18,"COMPANYNAME":"华为","ID":1,"NAME":"zhangsan","REMARK":"","SEX":"Male"}
user2  {"AGE":0,"COMPANYNAME":"小米","ID":2,"NAME":"Lisi","REMARK":"","SEX":"Female"}
```

#### 1-4-2. 使用fastjson

```json
import com.alibaba.fastjson.JSONObject;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null);
        User user2 = new User(2, "Lisi", "Female", null, "小米", "");
        String jsonString1 = JSONObject.toJSONString(user1);
        String jsonString2 = JSONObject.toJSONString(user2);
        System.out.println("user1  " + jsonString1);
        System.out.println("user2  " + jsonString2);
    }

}

结果：首字母自动变成小写,自动去掉了值为null的节点(为""的仍然保留)
user1  {"aGE":18,"cOMPANYNAME":"华为","iD":1,"nAME":"zhangsan","sEX":"Male"}
user2  {"cOMPANYNAME":"小米","iD":2,"nAME":"Lisi","rEMARK":"","sEX":"Female"}
```

## 2. 结论

### 2-1. fastjson

1. 无论对象首字母是大写，或者是小写，转json字符串是都会将首字母自动变成小写。
2. 默认会将值为null的节点忽略掉
3. 可通过注解@JSONField，指定对象转json时的key

### 2-2. json-lib

1. 只有在对象属性名全部为大写时，才不会将首字母变为小写，其他情况都会将首字母变成小写
2. 依然会保留值为null的节点

## 3. 对Date类型的字段格式化的处理

### 3-1. fastjson

默认会将时间转为毫秒值，可使用`JSONObject.toJSONStringWithDateFormat`或者在属性字段上使用`@JSONField(format = "yyyy-MM-dd HH:mm:ss")`

```java
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null, new Date());
        User user2 = new User(2, "Lisi", "Female", null, "小米", "", new Date());
        String jsonString1 = JSONObject.toJSONStringWithDateFormat(user1, "yyyy-MM-dd");
        String jsonString2 = JSONObject.toJSONString(user2);
        System.out.println("user1  " + jsonString1);
        System.out.println("user2  " + jsonString2);
    }

}

结果：
user1  {"Age":18,"CompanyName":"华为","Id":1,"Name":"zhangsan","Sex":"Male","birthday":"2019-05-21"}
user2  {"CompanyName":"小米","Id":2,"Name":"Lisi","Remark":"","Sex":"Female","birthday":1558398124477}
```



### 3-2. json-lib

默认会将Date类型的字段分解, 可通过传入`JsonConfig`并注册`JsonValueProcessor`解决这个问题

```java
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import java.util.Date;

public class JSONUtilsTest {

    public static void main(String[] args) {
        User user1 = new User(1, "zhangsan", "Male", 18, "华为", null, new Date());
        User user2 = new User(2, "Lisi", "Female", null, "小米", "", new Date());
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
        JSONObject jsonObject1 = JSONObject.fromObject(user1, jsonConfig);
        JSONObject jsonObject2 = JSONObject.fromObject(user2);
        System.out.println("user1  " + jsonObject1.toString());
        System.out.println("user2  " + jsonObject2.toString());
    }

}

user1  {"age":18,"birthday":"2019-05-21 08:30:53","companyName":"华为","id":1,"name":"zhangsan","remark":"","sex":"Male"}
user2  {"age":0,"birthday":{"date":21,"day":2,"hours":8,"minutes":30,"month":4,"seconds":53,"time":1558398653824,"timezoneOffset":-480,"year":119},"companyName":"小米","id":2,"name":"Lisi","remark":"","sex":"Female"}

public class DateJsonValueProcessor implements JsonValueProcessor {

    private String formatDateTime = "yyyy-MM-dd HH:mm:ss";

    private String formatDate = "yyyy-MM-dd";

    public DateJsonValueProcessor() {

    }

    public DateJsonValueProcessor(String format) {
        this.formatDateTime = format;
    }

    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        String[] obj = {};
        if (value instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(formatDateTime);
            Date[] dates = (Date[]) value;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {
                obj[i] = sf.format(dates[i]);
            }
        }
        return obj;
    }

    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof Date) {
            String str = "";
            if (Objects.equals(key, "checkDate") || Objects.equals(key, "validUntil")) {
                str = new SimpleDateFormat(formatDate).format((Date) value);
            } else {
                str = new SimpleDateFormat(formatDateTime).format((Date) value);
            }
            return str;
        }
        return value;
    }
}
```

