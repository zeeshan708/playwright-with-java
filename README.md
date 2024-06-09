# ZEE Test Automation Framwork

Note:The readme file is applicable for windows.

# Introduction

This repository contains ZEE test automation framework built using following components.

1. [Playwright with Java](https://playwright.dev/java/)

   - The framework has been created using playwright with java test automation tool. Playwright is a Java library to automate Chromium, Firefox and WebKit with a single API.

2. [Gradle](https://gradle.org/)

   - Gradle is a build tool used for building the project.

3. [TestNg](https://testng.org/doc/)

   - Testng is a testing framework used for test management.

4. [ReportPortal](https://reportportal.io/)

   - Report Portal is reporting tool used for reporting purpose. It captures the test results immediately after the test is completed.

5. [Log4j](https://logging.apache.org/log4j/2.x/)

   - Log4j is a logging framework for java applications. It captures the logs and every kind of info during the execution.

6. [ProjectLombok](https://projectlombok.org/)

   - Project Lombok is a library that helps you to write clear, concise, and less repetitive Java code. It makes up for
     some of the shortcomings within the core of Java that will most likely never be improved.

## Project Structure

```bash
playwright-java-framework/
│
├── src/
│ ├── main/
│ │ └── java/
│ │ └──  └── com.company.automtation/
│ │                         └── Helperclasses/
│ │ └──                            └── BrowserFactory.java
│ │ └──                     └── Page Classes/
│ │ └──                            └── BasePage.java
│ │ └──  ── Resources/
│ │ └──           └── log4j.xml
│ ├── test/
│ │ └── java/
│ │ └── └── com.company.automation.e2e/
│ │ └──                 └── Login/
│ │ └──                     └── LoginTest.java
│ │ └── Resources/
│ │ └──     └──Suites/
│ │ └──         └── ZEE.xml
│ │ └── testData.STAGING/
│ │         └── login/
│ │         └   ── login.json
├── .idea
├── configs
├── gradle
├── logs
├── pipelines
├── report
├── .gitignore
├── azurepiplines.yml
└── build.gradle
└── gradlew
└── gradlew.bat
└── README.md
└── settings.gradle
```

# Getting Started

TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:

#### Pre-requisites

- Java Development Kid (JDK) 11
- Gradle v7.5
- Intellij IDE Community Edition
- git (version control)

### Pre-requisites Installation

- Install JDK 11 in your local system and set java home and bin foler path.
- Download the Gradle in your system and set the bin folder path in system variables.
- Intall the Intellij community edition in your local system
- Install git in your local system

### Cloning the Project

- Open the folder in which you want to clone the project in intellij IDE:
- Open intellij terminal and hit command

```bash
 git clone https://github.com/zeeshan708/playwright-with-java.git
```

- Switch to main branch using if not in main branch

```bash
git checkout main
```

# Build and Test

- To build the project using command

```bash
   gradlew build
```

- To run the testcases in headed mode use below command

```bash
   ./gradlew clean ZEE -Dbrowser=chromium -Dheadless=false -Denv=staging -DreportPortal=disabled
```

- To run the testcases in headedless mode use below command

```bash
   ./gradlew clean ZEE -Dbrowser=chromium -Dheadless=true -Denv=staging -DreportPortal=disabled
```

# Reports

Once the execution is completed. The report is available in index.html
