<img src="https://img.enndfp.cn/202401181958108.png" style="zoom:80%;"/>

**简体中文** | [English](README-EN.md) 

<div align="center">
<h1>🌟 SimpleFramework</h1>
</div>

<div align="center">
<b>🛠️ 造轮子项目：从头实现Spring框架</b>
</div>
<div align="center">
<img src="https://img.shields.io/badge/Java-1.8-orange"/>
<img src="https://img.shields.io/badge/CGLIB-3.3.0-green"/>
<img src="https://img.shields.io/badge/AspectJWeaver-1.9.5-yellowgreen"/>
<img src="https://img.shields.io/badge/javax.servlet.jsp--api-2.3.3-blue"/>
<img src="https://img.shields.io/badge/javax.servlet--api-4.0.1-lightgrey"/>
<img src="https://img.shields.io/badge/Gson-2.8.6-yellow"/>
<img src="https://img.shields.io/badge/Slf4j--log4j12-1.7.28-yellow"/>
<img src="https://img.shields.io/badge/Lombok-1.18.30-blue"/>
</div>



## 📖 项目简介

> Spring框架在Java开发界占据了举足轻重的地位，这主要归功于其易于理解和功能强大的特性。它广泛应用了多种设计模式，为项目提供了规范化的架构。更重要的是，Spring作为一个开源框架，为广大开发者提供了学习和提升的机会，为Java开发带来了一种革新的春风。
>
> 鉴于Spring的这些优势，很多Java开发者渴望使用基础技术来实现一个类似于Spring的框架。这种做法不仅是对Spring架构和设计理念的深入理解，也是一种技术能力的展示。因此，“simple-framework”项目应运而生，旨在通过实现一个简化版的Spring框架，使开发者更容易地理解其核心概念，同时也能够提升自己在Java开发领域的技术水平。

**Simple-Framework是一个免费的开源项目，面向所有个人和企业，提供易于使用和学习的Java开发框架，支持开发者社区的共同进步与创新。**

## 🚀 技术亮点

- **Java 1.8**: 提供优化的性能和稳定性，是Java开发的基石。
- **CGLIB 3.3.0 & AspectJWeaver 1.9.5**: 强大的库，为AOP提供了坚实的基础。
- **Java Servlet API & Gson & Lombok**: 这些技术共同构成了一个强大的Web应用开发环境。

## 📚 项目架构图

### 🔄 IOC

![image-20240118205412786](https://img.enndfp.cn/202401182054883.png)

### 🔀 AOP

![image-20240119133233090](https://img.enndfp.cn/202401191332233.png)

### 🕸️ MVC

![image-20240119142136722](https://img.enndfp.cn/202401191421810.png)

## ✨ 主要功能

本项目是一个**简易版本的Spring框架**，实现了Spring框架的三大核心功能：**IOC**（控制反转）、**AOP**（面向切面编程）和**MVC**（模型-视图-控制器），并将其分为以下核心包：

#### 📦 Core包

**功能**: Core包实现了框架的**核心功能**，包括**Bean的扫描加载**、**容器的维护**、**单例模式的实现**，以及**自定义Bean的处理**。

**实现方式**: 利用**Java反射机制**动态扫描和加载指定包下的类，识别并处理不同类型的注解（如 `@Component`, `@Controller` 等）以管理不同种类的Bean。同时，它实现了**单例模式**，确保每个Bean只被实例化一次，并提供了操作Bean的基本方法，例如添加、获取和管理Bean实例。

#### 💉 Inject包

**功能**: Inject包负责**依赖注入**，包括处理 `@Autowired` 注解，实现**单例模式下的依赖注入**，以及为**接口注入实现类**。

**实现方式**: 通过**Java反射机制**扫描Bean的字段，查找带有 `@Autowired` 注解的字段，并利用Bean容器获取并注入所需依赖。它支持单例模式下的依赖注入，确保依赖的一致性和唯一性。同时，它也能为接口动态地注入适当的实现类，提高了代码的灵活性和可维护性。

#### 🔍 AOP包

**功能**: AOP包遵循**面向切面编程思想**，使用 `Aspect` 和 `Order` 注解来标识和排序切面类，通过**CGlib动态代理**和**AspectJWeaver**实现横切逻辑的织入，动态修改方法逻辑。

**实现方式**: 利用**CGlib**创建目标类的代理，并通过实现 `MethodInterceptor` 接口来拦截方法调用。这允许在方法执行前后执行切面逻辑（如日志记录、权限检查等）。同时，通过**AspectJ**的表达式语言提供对被代理类更精细的控制，使得可以根据不同的需要对方法逻辑进行修改和增强。

#### 🌐 MVC包

**功能**: MVC包处理**请求分发相关功能**，包括重构 `DispatcherServlet`，实现 `RequestProcessorChain` 和 `RequestProcessor` 矩阵，以及 `ResultRender` 矩阵，完成多种请求的处理与响应渲染。

**实现方式**: 通过 `DispatcherServlet` 作为**中心控制器**，处理所有的HTTP请求并将其分发到相应的处理器。利用 `RequestProcessorChain` 管理和执行一系列请求处理器，以处理不同类型的请求（如静态资源、控制器方法等）。`ResultRender` 矩阵负责根据处理结果选择合适的渲染策略，例如渲染HTML页面或返回JSON数据，确保响应正确地渲染和返回给客户端。

## 💡 快速上手指南

要开始使用 **SimpleFramework**，您可以采取以下步骤：

### 📥 方法一：源码使用

1. 克隆仓库：

   ```bash
   git clone https://github.com/Enndfp/simple-framework.git
   ```

2. 导入项目到您的IDE（例如IntelliJ IDEA）。

3. 在 `demo` 目录下进行相关测试。这与使用Spring Boot开发项目类似。

### 📦 方法二：War包部署

1. 构建项目并生成War包。
2. 将War包部署到您的Servlet容器中，如Apache Tomcat。
3. 启动容器，应用将自动部署。

### 🌟 示例代码

以下是一个简单的示例，展示了如何在您的项目中使用 **SimpleFramework**：

```java
import com.simpleframework.core.BeanContainer;

public class MyApplication {
    public static void main(String[] args) {
        // 初始化容器
        BeanContainer container = BeanContainer.getInstance();
        container.loadBeans("com.yourpackage");

        // 使用容器获取Bean
        MyService myService = (MyService) container.getBean(MyService.class);
        myService.doSomething();
    }
}
```

在这个例子中，我们首先获取了 `BeanContainer` 的实例，然后加载了指定包路径下的所有Bean。之后，我们从容器中获取了 `MyService` 类的实例，并调用了其方法。
