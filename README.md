## Description

This library is an accelerator for Selenium.  It can be used to quickly develop Selenium scripts in Java.


## Usage

Instead of writing this:

```java
WebElement username;

if (driver.findElements(By.name("username")).size() > 0) {
  username = driver.findElements(By.name("username"));
} else if (driver.findElements(By.name("username")).size() > 0) {
  username = driver.findElements(By.id("username"));
} else if {
  // additional search methods
}

username.sendKeys("user1#");
```

You can write:

```java

sh.setText("username", "user1");
```

test