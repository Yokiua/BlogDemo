/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.31 : Database - blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `blog`;

/*Table structure for table `t_blog` */

DROP TABLE IF EXISTS `t_blog`;

CREATE TABLE `t_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commentabled` tinyint(1) NOT NULL,
  `content` longtext,
  `createTime` datetime(6) DEFAULT NULL,
  `firstPicture` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `published` tinyint(1) NOT NULL,
  `recommend` tinyint(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updateTime` datetime(6) DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `tagIds` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`),
  KEY `FK292449gwg5yf7ocdlmswv9w4j` (`type_id`),
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `t_blog` */

insert  into `t_blog`(`id`,`commentabled`,`content`,`createTime`,`firstPicture`,`flag`,`published`,`recommend`,`title`,`updateTime`,`views`,`type_id`,`user_id`,`tagIds`,`description`) values 
(26,1,'### 我的目录结构\r\n[![目录结构](http://192.168.137.1:8099/static/imges/blog1.1.jpg \"目录结构\")](http://192.168.137.1:8099/static/imges/blog1.1.jpg \"目录结构\")\r\n\r\n默认是无法访问到静态资源的,若要访问,可以添加配置文件：\r\n```java\r\n\r\nimport org.springframework.context.annotation.Configuration;\r\nimport org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;\r\nimport org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;\r\n\r\n/**\r\n * 开启静态资源访问的配置类\r\n */\r\n@Configuration\r\npublic class StaticConfig extends WebMvcConfigurerAdapter {\r\n\r\n    public void addResourceHandlers(ResourceHandlerRegistry registry) {\r\n        //将所有/static/** 访问都映射到classpath:/static/ 目录下\r\n        registry.addResourceHandler(\"/static/**\").addResourceLocations(\"classpath:/static/\");\r\n    }\r\n\r\n}\r\n\r\n```\r\n#### ### 再次测试后,将可以访问static目录下的静态资源','2020-09-14 08:49:32.902000','../static/imges/blog1.jpeg','原创',1,1,'SpringBoot访问不到静态资源的解决方法','2020-09-14 08:53:54.504000',181,24,1,'10,15','虽然现在的主流是前后端分离，但在个人使用SpringBoot做项目时，经常会需要访问到静态资源，而高版本的boot又默认拦截，所以需要开启静态资源的访问'),
(27,1,'## 通过如下设置,就可以实现同步请求:\r\n由于$.post() 和 $.get() 默认是 异步请求，如果需要同步请求，则可以进行如下使用：\r\n\r\n在$.post()前把ajax设置为同步：$.ajaxSettings.async = false;\r\n\r\n在$.post()后把ajax改回为异步：$.ajaxSettings.async = true;\r\n\r\n如：\r\n```javascript\r\n $.ajaxSettings.async = false;\r\n    $.post(\"/finance/getLastTimeCard\", data, function(result) {\r\n		// 请求处理\r\n    },\"json\");\r\n    $.ajaxSettings.async = true;\r\n```','2020-09-14 08:58:11.816000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600083951538&di=2212a2835288c836e787d7cd9c049571&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fa%2F584a1cfa2f02f.jpg','转载',1,1,'$.post() 和 $.get() 如何同步请求','2020-09-14 08:58:11.816000',52,31,1,'11,12,14','JQuery框架中关于AJAX的使用时，$.post() 和 $.get() 默认是 异步请求，如果需要同步请求，这可以通过设置async属性的方式修改,具体参考博客'),
(28,1,'## Select\r\n```java\r\nList<T> select(T record);\r\n```\r\n\r\n说明：根据实体中的属性值进行查询，查询条件使用等号\r\n\r\n```java\r\nT selectByPrimaryKey(Object key);\r\n```\r\n\r\n说明：根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号\r\n\r\n```java\r\nList<T> selectAll();\r\n```\r\n\r\n说明：查询全部结果，select(null)方法能达到同样的效果\r\n\r\n```java\r\nT selectOne(T record);\r\n```\r\n\r\n说明：根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号\r\n\r\n```java\r\njava int selectCount(T record);\r\n```\r\n\r\n说明：根据实体中的属性查询总数，查询条件使用等号\r\n\r\n## Insert\r\n\r\n```java\r\nint insert(T record);\r\n```\r\n\r\n说明：保存一个实体，null的属性也会保存，不会使用数据库默认值\r\n\r\n```java\r\nint insertSelective(T record);\r\n```\r\n\r\n说明：保存一个实体，null的属性不会保存，会使用数据库默认值\r\n\r\n## Update\r\n\r\n```java\r\nint updateByPrimaryKey(T record);\r\n```\r\n\r\n说明：根据主键更新实体全部字段，null值会被更新\r\n\r\n```java\r\nint updateByPrimaryKeySelective(T record);\r\n```\r\n\r\n说明：根据主键更新属性不为null的值\r\n\r\n## Delete\r\n\r\n```java\r\nint delete(T record);\r\n```\r\n\r\n说明：根据实体属性作为条件进行删除，查询条件使用等号\r\n\r\n```java\r\nint deleteByPrimaryKey(Object key);\r\n```\r\n\r\n说明：根据主键字段进行删除，方法参数必须包含完整的主键属性\r\n\r\n## Example方法\r\n```java\r\nList<T> selectByExample(Object example);\r\n```\r\n\r\n说明：根据Example条件进行查询\r\n**重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列**\r\n\r\n```java\r\nint selectCountByExample(Object example);\r\n```\r\n\r\n**说明：根据Example条件进行查询总数**\r\n\r\n```java\r\nint updateByExample(@Param(\"record\") T record, @Param(\"example\") Object example);\r\n```\r\n\r\n**说明：根据Example条件更新实体record包含的全部属性，null值会被更新**\r\n\r\n```java\r\nint updateByExampleSelective(@Param(\"record\") T record, @Param(\"example\") Object example);\r\n```\r\n\r\n**说明：根据Example条件更新实体record包含的不是null的属性值**\r\n\r\n```java\r\nint deleteByExample(Object example);\r\n```\r\n\r\n**说明：根据Example条件删除数据**\r\n\r\n## 总结\r\n通用Mapper的原理是通过反射获取实体类的信息，构造出相应的SQL，因此我们只需要维护好实体类即可，对于应付复杂多变的需求提供了很大的便利。上文叙述的只是通用Mapper的简单用法，在实际项目中，还是要根据业务，在通用Mapper的基础上封装出粒度更大、更通用、更好用的方法。','2020-09-14 09:16:11.709000','http://images.jasonpang.top/%E7%8E%89%E5%AD%904.jpg','转载',1,1,'Mybatis框架中通用Mapper的方法介绍','2020-09-14 09:29:12.432000',11,33,1,'10,16,17','通用Mapper的原理是通过反射获取实体类的信息，构造出相应的SQL，因此我们只需要维护好实体类即可，对于应付复杂多变的需求提供了很大的便利。'),
(29,1,'## @JsonFormat 使用\r\n我们可以有两种用法（我知道的），在对象属性上，或者在属性的 getter 方法上，如下代码所示：\r\n\r\n增加到属性上：\r\n```java\r\n/**更新时间  用户可以点击更新，保存最新更新的时间。**/\r\n@JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")\r\nprivate Date updateTime;\r\n```\r\n增加到 **getter** 方法上：\r\n```java\r\n@JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")\r\npublic Date getUpdateTime() {\r\n    return updateTime;\r\n}\r\n```\r\n**以上结果输出都是一样的。具体输出格式，调整 **pattern** 。**\r\n## @JsonFormat 相差8小时问题\r\n上面直接这么使用，在我们中国来讲和我们的北京时间，会相差8个小时，因为我们是东八区（北京时间）。\r\n\r\n所以我们在格式化的时候要指定时区（**timezone** ），代码如下：\r\n```java\r\n/**更新时间  用户可以点击更新，保存最新更新的时间。**/\r\n@JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\",timezone=\"GMT+8\")\r\nprivate Date updateTime;\r\n```\r\n也就是增加一个属性，**timezone=\"GMT+8\"** 即可，**getter** 方法我就不写了，一样的。\r\n\r\n咱看看结果，我这个接口就是这么输出的：公安网备查询 ，以 https://www.sojson.com/api/gongan/sina.com.cn 为例。\r\n```json\r\n{\r\n    \"data\": {\r\n        \"id\": \"11000002000016\",\r\n        \"sitename\": \"新浪网\",\r\n        \"sitedomain\": \"sina.com.cn\",\r\n        \"sitetype\": \"交互式\",\r\n        \"cdate\": \"2016-01-21\",\r\n        \"comtype\": \"企业单位\",\r\n        \"comname\": \"北京新浪互联信息服务有限公司\",\r\n        \"comaddress\": \"北京市网安总队\",\r\n        \"updateTime\": \"2017-09-05 02:26:34\" //看这...这里就是刚刚输出的。\r\n    },\r\n    \"status\": 200\r\n}\r\n```','2020-09-14 09:38:01.714000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600086309756&di=58128aba5681ae3b02e9ee84506fa373&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fe%2F56011dec3a39a.jpg','转载',1,1,'时间格式化，时间注解@JsonFormat ','2020-09-14 09:38:01.714000',6,33,1,'10,13,15','Jackson  是  SpringMvc  官方推荐结合的，其实我是习惯用   Gson  的，但是由于公司统一使用   Jackson  ，自然对   Jackson  需要关注的更多。下面来说说其中一个注解，就是 @JsonFormat 。'),
(30,1,'####项目在添加了测试类时候打包出现的问题\r\n```\r\n[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.22.1:test (default-test) on project handset: Unable to generate classpath: org.apache.maven.artifact.resolver.MultipleArtifactsNotFoundException: Missing:\r\n[ERROR] ----------\r\n[ERROR] 1) org.apache.maven.surefire:surefire-junit4:jar:2.22.1\r\n[ERROR] \r\n[ERROR] Try downloading the file manually from the project website.\r\n[ERROR] \r\n[ERROR] Then, install it using the command:\r\n[ERROR] mvn install:install-file -DgroupId=org.apache.maven.surefire -DartifactId=surefire-junit4 -Dversion=2.22.1 -Dpackaging=jar -Dfile=/path/to/file\r\n[ERROR] \r\n[ERROR] Alternatively, if you host your own repository you can deploy the file there:\r\n[ERROR] mvn deploy:deploy-file -DgroupId=org.apache.maven.surefire -DartifactId=surefire-junit4 -Dversion=2.22.1 -Dpackaging=jar -Dfile=/path/to/file -Durl=[url] -DrepositoryId=[id]\r\n[ERROR] \r\n[ERROR] Path to dependency:\r\n[ERROR] 1) dummy:dummy:jar:1.0\r\n[ERROR] 2) org.apache.maven.surefire:surefire-junit4:jar:2.22.1\r\n[ERROR] \r\n[ERROR] ----------\r\n[ERROR] 1 required artifact is missing.\r\n[ERROR] \r\n[ERROR] for artifact:\r\n[ERROR] dummy:dummy:jar:1.0\r\n[ERROR] \r\n[ERROR] from the specified remote repositories:\r\n[ERROR] alimaven (http://maven.aliyun.com/nexus/content/groups/public/, releases=true, snapshots=false)\r\n[ERROR] -> [Help 1]\r\n[ERROR] \r\n[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.\r\n[ERROR] Re-run Maven using the -X switch to enable full debug logging.\r\n[ERROR] \r\n[ERROR] For more information about the errors and possible solutions, please read the following articles:\r\n[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException\r\n \r\nProcess finished with exit code 1\r\n```\r\n解决办法\r\n\r\n既不是JDK问题也不是版本问题,在参考了maven打包时跳过测试中根据文章提示\r\n\r\n在pom.xml最下面添加配置就好了, (**但感觉并不是最终方法**)\r\n```xml\r\n<build>\r\n<plugins>\r\n	<plugin>\r\n		<groupId>org.springframework.boot</groupId>\r\n		<artifactId>spring-boot-maven-plugin</artifactId>\r\n		</plugin>\r\n		<!--添加配置跳过测试-->\r\n	<plugin>\r\n		<groupId>org.apache.maven.plugins</groupId>\r\n		<artifactId>maven-surefire-plugin</artifactId>\r\n		<version>2.22.1</version>\r\n			<configuration>\r\n			<skipTests>true</skipTests>\r\n			</configuration>\r\n	</plugin>\r\n		<!--添加配置跳过测试-->\r\n</plugins>\r\n</build>\r\n```\r\n','2020-09-14 09:45:44.011000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600086327204&di=d9d61ddeca23fd46d15a0463d56c5539&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201706%2F28%2F20170628174828_Fv42z.jpeg','原创',1,1,'MAVEN打包异常处理之测试类异常','2020-09-14 09:47:05.475000',4,37,1,'10,20','Maven打包时，有时会出现各种问题，比如 ：Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:2.22.1:test'),
(31,1,'### Notify、NotifyAll、Wait实现线程将的协作\r\nwait/notify和notifyAll 的使用场景（生产者/消费者）\r\n\r\n消费者（等待方）：\r\n1. 获取对象的锁\r\n2. 是否有商品，没有着调用wait方法进行等待\r\n3. 条件满足则执行业务逻辑\r\n\r\n------------\r\n\r\n\r\n\r\n生产者（接收方）：\r\n1. 获取对象的锁\r\n2. 补充商品（改变条件）\r\n3. 通知所有在该对象上等待的线程\r\n\r\n------------\r\n\r\n\r\n代码实现：\r\n```java\r\npublic class WaitNotify {\r\n\r\n    private static boolean flag = true;\r\n    private static final Commodity lock = new Commodity();\r\n\r\n    public static void main(String[] args) throws Exception{\r\n        Thread consumer = new Thread(new Consumer());\r\n        Thread producer = new Thread(new Producer());\r\n        producer.start();\r\n        SleepUtils.second(1);\r\n        consumer.start();\r\n    }\r\n    \r\n    private static class Consumer implements Runnable{\r\n        @Override\r\n        public void run() {\r\n            synchronized (lock){\r\n                while(true){\r\n                    if(lock.count <= 0){\r\n                        try {\r\n                            System.out.println(\"货物消费完毕，等待补充 ... \");\r\n                            lock.notifyAll();\r\n                            lock.wait();\r\n                        } catch (InterruptedException e) {\r\n                            e.printStackTrace();\r\n                        }\r\n                    }else{\r\n                        lock.count -- ;\r\n                        System.out.println(\"消费一件商品 ... \");\r\n                        SleepUtils.second(1);\r\n                    }\r\n                }\r\n            }\r\n        }\r\n    }\r\n    private static class Producer implements Runnable{\r\n        @Override\r\n        public void run() {\r\n            while(true){\r\n                synchronized (lock){\r\n                    lock.count = 10 ;\r\n                    System.out.println(\"补充商品\");\r\n                    lock.notifyAll();\r\n                    try {\r\n                        lock.wait();\r\n                    } catch (InterruptedException e) {\r\n                        e.printStackTrace();\r\n                    }\r\n                }\r\n            }\r\n        }\r\n    }\r\n    private static class Commodity{\r\n        private int count ;\r\n    }\r\n}\r\n/*\r\n程序输出：\r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n货物消费完毕，等待补充 ... \r\n补充商品\r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n消费一件商品 ... \r\n货物消费完毕，等待补充 ... \r\n*/\r\n```\r\n### 简单线程池的实现\r\n```java\r\npublic class ConnectionPool {\r\n\r\n    private LinkedList<Connection> pool = new LinkedList<>();\r\n\r\n    public ConnectionPool(int initialSize){\r\n        if(initialSize > 0){\r\n            for (int i = 0; i < initialSize; i++) {\r\n                pool.addLast(ConnectionDriver.createConnection());\r\n            }\r\n        }\r\n    }\r\n\r\n    public void releaseConnection(Connection connection){\r\n        if(connection != null){\r\n            synchronized (pool){\r\n                pool.addLast(connection);\r\n\r\n                // 当有线程归还连接时进行通知\r\n                // 目的是让消费者能感知到连接池中有连接\r\n                pool.notifyAll();\r\n            }\r\n        }\r\n    }\r\n\r\n    // 带超时时间的连接获取方式\r\n    public Connection fetchConnection(long mills) throws InterruptedException{\r\n        synchronized (pool){\r\n            // 不设置超时时间\r\n            if(mills <=0){\r\n                while(pool.isEmpty()){\r\n                    pool.wait();\r\n                }\r\n                return pool.removeFirst();\r\n            }else{\r\n                long future = System.currentTimeMillis() + mills ;\r\n                long remain = mills ;\r\n                while(pool.isEmpty() && remain > 0){\r\n                    pool.wait(remain);\r\n                    //刷新剩余时间\r\n                    remain = future - System.currentTimeMillis();\r\n                }\r\n\r\n                Connection result = null ;\r\n                //有可能 线程池中有线程 也有可能过了超时时间\r\n                if(!pool.isEmpty()){\r\n                    result = pool.removeFirst();\r\n                }\r\n\r\n                return result;\r\n            }\r\n        }\r\n    }\r\n}\r\n```\r\n### 自定义简单的线程池\r\n```java\r\npublic class DefaultThreadPool {\r\n\r\n    //最大工作线程数\r\n    private static final int MAX_WORKER_NUMBERS = 10;\r\n\r\n    //默认工作线数\r\n    private static final int DEFAULT_WORKER_NUMBER = 5 ;\r\n\r\n    //最小工作线程数\r\n    private static final int MIN_WORKER_NUMBERS = 1;\r\n\r\n    //任务列表，存储用户提交的任务信息\r\n    private final LinkedList<Job> jobs = new LinkedList<>();\r\n\r\n    //工作线程列表\r\n    private final LinkedList<Worker> workers = new LinkedList<>();\r\n\r\n    //工作线程数量\r\n    private int workerNum = DEFAULT_WORKER_NUMBER ;\r\n\r\n    public DefaultThreadPool(){\r\n        initializeWorker(MIN_WORKER_NUMBERS);\r\n    }\r\n    public DefaultThreadPool(int num){\r\n        initializeWorker(num);\r\n    }\r\n    /**\r\n     * 添加任务到任务队列，并且按序执行\r\n     * @param job\r\n     */\r\n    public void execute(Job job){\r\n        if(job == null)\r\n            return ;\r\n        synchronized (jobs){\r\n            jobs.addLast(job);\r\n            // 通知worker 有任务进来了\r\n            jobs.notifyAll();\r\n        }\r\n    }\r\n    public void shutdown(){\r\n        for (Worker worker : workers){\r\n            worker.shutdown();\r\n        }\r\n    }\r\n    public void addWorker(int num){\r\n        if(num < 0){\r\n            throw new IllegalArgumentException(\"参数异常\");\r\n        }\r\n        // 当前还能添加多少个worker\r\n        int maxRemain = MAX_WORKER_NUMBERS - workerNum ;\r\n        num = Math.min(num, maxRemain);\r\n        for (int i = 0; i < num; i++) {\r\n            Worker worker = new Worker();\r\n            workers.add(worker);\r\n        }\r\n    }\r\n    public void removeWorker(int num){\r\n        if(num > workerNum - MIN_WORKER_NUMBERS){\r\n            throw new IllegalArgumentException(\"参数异常\");\r\n        }\r\n        int count = 0;\r\n        while(count < num){\r\n            Worker worker = workers.get(count);\r\n            if(workers.remove(worker)){\r\n                worker.shutdown();\r\n                count++;\r\n            }\r\n        }\r\n        workerNum -= num;\r\n    }\r\n    public void initializeWorker(int num){\r\n        num = Math.max(num,MIN_WORKER_NUMBERS);\r\n        for (int i = 0; i < num; i++) {\r\n            Worker worker = new Worker();\r\n            workers.add(worker);\r\n        }\r\n    }\r\n\r\n    // 用户提交的任务\r\n    interface Job{\r\n        void run();\r\n    }\r\n    // 工作线程\r\n    private class Worker implements Runnable{\r\n        private volatile boolean running = true;\r\n        /**\r\n         * 获取任务列表中的任务执行\r\n         */\r\n        @Override\r\n        public void run() {\r\n            Job job = null ;\r\n            while(running){\r\n                synchronized (jobs){\r\n                    // 任务列表为空，进行等待\r\n                    while(jobs.isEmpty()){\r\n                        try {\r\n                            this.wait();\r\n                        } catch (InterruptedException e) {\r\n                            // 对中断进行响应\r\n                            Thread.currentThread().interrupt();\r\n                            return ;\r\n                        }\r\n                    }\r\n                    // 任务队列中有任务\r\n                    // 获取任务并且释放锁\r\n                    job = jobs.removeFirst();\r\n                }\r\n                if(job != null){\r\n                    //执行job方法\r\n                    job.run();\r\n                }\r\n            }\r\n        }\r\n        public void shutdown(){\r\n            this.running = false;\r\n        }\r\n    }\r\n}\r\n```','2020-09-14 09:55:09.784000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600087361672&di=a112a8646116286e37b3f6bc7cfbeb40&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Ffac0025858e5b6bc0d990c4283c6ee8dcd6d4f58.jpg','原创',1,1,'Notify、NotifyAll、Wait实现线程将的协作','2020-09-14 09:55:09.784000',28,24,1,'10','Notify、NotifyAll、Wait实现线程将的协作，来模拟生产者和消费者的模型'),
(32,1,'## vuejs标签属性数据绑定和拼接 v-bind:href= 拼接字符串\r\nv-bind绑定href 的值（拼接属性值 String）\r\n参考csdn文章：[终于知道vue.js标签属性数据绑定和拼接](https://blog.csdn.net/jianyuqi0215/article/details/60139650 \"终于知道vue.js标签属性数据绑定和拼接\")\r\n\r\n**代码实例如下：**\r\n```html\r\n<a class=\"didi-link\" v-bind:href=\"[\'xxx/detail/\'+item.Id]\" >\r\n{{item.Name}}\r\n</a>\r\n```','2020-09-14 10:00:01.316000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600087636754&di=860fc41f351aa3ba899f9cfe0362b935&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20180803%2F22%2F1533305026-ieYxkRUqfp.jpg','转载',1,1,'v-bind:href= 拼接字符串','2020-09-14 10:00:32.282000',3,31,1,'19','v-bind:href= 拼接字符串来拼接字符串，可以动态的设置href的属性，达到想要的效果'),
(33,1,'### 第一种：通过一个布尔值判断样式类是否生效\r\n\r\n**//isActive 是在data里面布尔值，   rotateRight 是 class 样式类 \r\n//isActive 为true时样式类 rotateRight 生效**\r\n\r\n\r\n```html\r\n<div :class=\"{rotateRight:isActive}\">abs</div>\r\n```\r\n\r\n### 第二种：通过三元运算符判断使用哪个样式类\r\n\r\n**//isActive 是在data里面布尔值 rotateRight 是 class 样式类 \r\n//isActive 为 true 时样式类 right 生效,为 false 时样式类 right2 生效**\r\n\r\n```html\r\n<div :class=\"[isActive ? \'right\' : \'right2\']\">abs</div>\r\n```\r\n\r\n固定的样式和动态绑定同时存在\r\nerrorClass 是始终存在的，isActive 为 true 时添加 activeClass 类：\r\n\r\n```html\r\n<div v-bind:class=\"[errorClass ,isActive ? activeClass : \'\']\"></div>\r\n```','2020-09-14 10:05:21.353000','http://images.jasonpang.top/b97f8b9fd012c11a99feeaef0eea096387b44d5b.jpg@1320w_958h.webp1585228339899','原创',1,1,'VUE 动态绑定class','2020-09-14 10:05:21.353000',5,31,1,'19','VUE 动态绑定class，可以动态的设置class的属性，达到想要的效果, v-bind:class 或 :class'),
(34,1,'```javascript\r\nvar con_name = getQueryString(\"con_name\"); //接收con_name\r\n        function getQueryString(value) {\r\n            var reg = new RegExp(\"(^|&)\" + value + \"=([^&]*)(&|$)\", \"i\");\r\n            var r = window.location.search.substr(1).match(reg);\r\n            if (r != null) return unescape(r[2]); return null;\r\n        }\r\n```\r\ncon_name就是通过url传过来的值！！\r\n\r\n原文地址：http://www.cnblogs.com/ITyouxiang1994/p/4264188.html','2020-09-14 10:09:58.785000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600088235791&di=ebe9aaed0c661b441e1412b36c2fa48c&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20190318%2F15%2F1552895117-updtivkNmQ.jpeg','转载',1,1,'JQuery获取请求的URL','2020-09-14 10:09:58.785000',4,31,1,'14','JQuery获取请求的URL，可以获取URL中的参数'),
(35,1,'网上广为流传的**/[\\u4e00-\\u9fa5]/ **其实只是cjk统一表意文字的基本部分\r\n\r\n简而言之，要解决这个问题，首先要搞懂 unicode 对汉字的定义， 注意区分汉字和汉文的区别（大牛博客里讲了）。\r\n然后就可以查找对应的unicode使用范围了。\r\n但是。。。\r\n时光飞逝，怎么写出一个可以自动与时俱进的匹配汉字的正则表达式呢？答案就是 Unicode属性类。\r\n顾名思义，就是将满足一定属性条件的unicode编码放在一起，这样书写正则表达式的时候就不用一个个的去取对应的范围了，比如我要找汉字\r\n使用unicode属性类就可以直接写成\r\n\r\n```javascript\r\n/\\p{Unified_Ideograph}/u\r\n```\r\n但是貌似现在并不是所有浏览器都支持这种正则表达式的写法，如果不支持就需要使用转换器进行优雅降级。','2020-09-14 10:14:13.828000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600088490311&di=9a5d2cdc22ce3d3dd9608a72bdc2f181&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201709%2F01%2F20170901000600_wd8Le.jpeg','原创',1,1,'js正则表达式匹配汉字','2020-09-14 10:14:13.828000',1,31,1,'11','js正则表达式匹配汉字，通常用于表单校验'),
(36,1,'**参考资料：好好学Java ** https://mp.weixin.qq.com/s/Dd_7yUh3lq3TqE2cjsYXvw\r\n\r\nJDK8新特性里提供了3个时间类：LocalDate、LocalTime、LocalDateTime\r\n\r\n在项目开发中，已经需要对Date类型进行格式，否则可读性很差，格式化Date类型要使用SimpleDateFormat，但SimpleDateFormat是现成不安全的。\r\n\r\n**1.为什么需要LocalDate、LocalTime、LocalDateTime\r\n1.1.Date如果不格式化，打印出的日期可读性差**\r\n\r\n```\r\nTue Sep 10 09:34:04 CST 2019\r\n```\r\n\r\n**1.2 使用SimpleDateFormat对时间进行格式化，但SimpleDateFormat是线程不安全的。SimpleDateFormat的format方法最终调用代码：**\r\n\r\n```java\r\nprivate StringBuffer format(Date date, StringBuffer toAppendTo, FieldDelegate delegate) {\r\n        // Convert input date to time field list\r\n        calendar.setTime(date);\r\n        boolean useDateFormatSymbols = useDateFormatSymbols();\r\n        for (int i = 0; i < compiledPattern.length; ) {\r\n            int tag = compiledPattern[i] >>> 8;\r\n            int count = compiledPattern[i++] & 0xff;\r\n            if (count == 255) {\r\n                count = compiledPattern[i++] << 16;\r\n                count |= compiledPattern[i++];\r\n            }\r\n            switch (tag) {\r\n                case TAG_QUOTE_ASCII_CHAR:\r\n                    toAppendTo.append((char) count);\r\n                    break;\r\n                case TAG_QUOTE_CHARS:\r\n                    toAppendTo.append(compiledPattern, i, count);\r\n                    i += count;\r\n                    break;\r\n                default:\r\n                    subFormat(tag, count, delegate, toAppendTo, useDateFormatSymbols);\r\n                    break;\r\n            }\r\n        }\r\n        return toAppendTo;\r\n    }\r\n```\r\n　  calendar是共享变量，并且这个共享变量没有做线程安全控制。当多个线程同时使用相同的SimpleDateFormat对象【如用static修饰的SimpleDateFormat】调用format方法时，多个线程会同时调用calendar.setTime方法，可能一个线程刚设置好time值 另外的一个线程马上把设置的time值给修改了导致返回的格式化时间可能是错误的。\r\n\r\n　　在多并发情况下使用SimpleDateFormat需格外注意 SimpleDateFormat除了format是线程不安全以外，parse方法也是线程不安全的。parse方法实际调用alb.establish(calendar).getTime()方法来解析，alb.establish(calendar)方法里主要完成了\r\n\r\n　　a、重置日期对象cal的属性值\r\n\r\n　　b、使用calb中中属性设置cal\r\n\r\n　　c、返回设置好的cal对象\r\n\r\n　　但是这三步不是原子操作。\r\n\r\n　　多线程并发如何保证线程安全 - 避免线程之间共享一个SimpleDateFormat对象，每个线程使用时都创建一次SimpleDateFormat对象 => 创建和销毁对象的开销大 - 对使用format和parse方法的地方进行加锁 => 线程阻塞性能差 - 使用ThreadLocal保证每个线程最多只创建一次SimpleDateFormat对象 => 较好的方法。Date对时间处理比较麻烦，比如想获取某年、某月、某星期，以及n天以后的时间，如果用Date来处理的话真是太难了，你可能会说Date类不是有getYear、getMonth这些方法吗，获取年月日很Easy，但都被弃用了。\r\n\r\n　　\r\n\r\n**2.Java8全新的日期和时间API**\r\n\r\n**2.1 LocalDate**\r\n\r\n　　LocalDate是日期处理类，具体API如下：\r\n\r\n```java\r\n// 获取当前日期\r\nLocalDate now = LocalDate.now();\r\n// 设置日期\r\nLocalDate localDate = LocalDate.of(2019, 9, 10);\r\n// 获取年\r\nint year = localDate.getYear();     //结果：2019\r\nint year1 = localDate.get(ChronoField.YEAR); //结果：2019\r\n// 获取月\r\nMonth month = localDate.getMonth();   // 结果：SEPTEMBER\r\nint month1 = localDate.get(ChronoField.MONTH_OF_YEAR); //结果：9\r\n// 获取日\r\nint day = localDate.getDayOfMonth();   //结果：10\r\nint day1 = localDate.get(ChronoField.DAY_OF_MONTH); // 结果：10\r\n// 获取星期\r\nDayOfWeek dayOfWeek = localDate.getDayOfWeek();   //结果：TUESDAY\r\nint dayOfWeek1 = localDate.get(ChronoField.DAY_OF_WEEK); //结果：2\r\n```\r\n\r\n**2.2 LocalTime**\r\n\r\n　　LocalTime是时间处理类，具体API如下：\r\n\r\n```java\r\n// 获取当前时间\r\nLocalTime now = LocalTime.now();\r\n// 设置时间\r\nLocalTime localTime = LocalTime.of(13, 51, 10);\r\n//获取小时\r\nint hour = localTime.getHour();    // 结果：13\r\nint hour1 = localTime.get(ChronoField.HOUR_OF_DAY); // 结果：13\r\n//获取分\r\nint minute = localTime.getMinute();  // 结果：51\r\nint minute1 = localTime.get(ChronoField.MINUTE_OF_HOUR); // 结果：51\r\n//获取秒\r\nint second = localTime.getSecond();   // 结果：10\r\nint second1 = localTime.get(ChronoField.SECOND_OF_MINUTE); // 结果：10\r\n```\r\n\r\n**2.3 LocalDateTime**\r\n\r\n　　LocalDateTime可以设置年月日时分秒，相当于LocalDate + LocalTime\r\n\r\n```java\r\n// 获取当前日期时间\r\nLocalDateTime localDateTime = LocalDateTime.now();\r\n// 设置日期\r\nLocalDateTime localDateTime1 = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);\r\nLocalDateTime localDateTime2 = LocalDateTime.of(localDate, localTime);\r\nLocalDateTime localDateTime3 = localDate.atTime(localTime);\r\nLocalDateTime localDateTime4 = localTime.atDate(localDate);\r\n// 获取LocalDate\r\nLocalDate localDate2 = localDateTime.toLocalDate();\r\n// 获取LocalTime\r\nLocalTime localTime2 = localDateTime.toLocalTime();\r\n```\r\n\r\n**2.4 Instant**\r\n\r\n```java\r\n// 创建Instant对象\r\nInstant instant = Instant.now();\r\n// 获取秒\r\nlong currentSecond = instant.getEpochSecond();\r\n// 获取毫秒\r\nlong currentMilli = instant.toEpochMilli();\r\n　　如果只是为了获取秒数或者毫秒数，使用System.currentTimeMillis()来得更为方便\r\n```\r\n\r\n**2.5 修改LocalDate、LocalTime、LocalDateTime、Instant**\r\n\r\n　　LocalDate、LocalTime、LocalDateTime、Instant为不可变对象，修改这些对象对象会返回一个副本。增加、减少年数、月数、天数等 以LocalDateTime为例。\r\n\r\n\r\n```java\r\n// 创建日期：2019-09-10 14:46:56\r\nLocalDateTime localDateTime = LocalDateTime.of(2019, Month.SEPTEMBER, 10, 14, 46, 56);\r\n//增加一年\r\nlocalDateTime = localDateTime.plusYears(1);  //结果： 2020-09-10 14:46:56\r\nlocalDateTime = localDateTime.plus(1, ChronoUnit.YEARS); //结果： 2021-09-10 14:46:56\r\n//减少一个月\r\nlocalDateTime = localDateTime.minusMonths(1);  //结果： 2021-08-10 14:46:56\r\nlocalDateTime = localDateTime.minus(1, ChronoUnit.MONTHS); //结果： 2021-07-10 14:46:56\r\n```\r\n\r\n　　通过with修改某些值，年月日时分秒都可以通过with方法设置。\r\n\r\n```java\r\n//修改年为2019\r\nlocalDateTime = localDateTime.withYear(2020);\r\n//修改为2022\r\nlocalDateTime = localDateTime.with(ChronoField.YEAR, 2022);\r\n```\r\n\r\n　　**日期计算**。比如有些时候想知道这个月的最后一天是几号、下个周末是几号，通过提供的时间和日期API可以很快得到答案 。TemporalAdjusters提供的各种日期时间格式化的静态类，比如firstDayOfYear是当前日期所属年的第一天\r\n\r\n```java\r\nLocalDate localDate = LocalDate.now();\r\nLocalDate localDate1 = localDate.with(TemporalAdjusters.firstDayOfYear());\r\n```\r\n\r\n 　　格式化时间。DateTimeFormatter默认提供了多种格式化方式，如果默认提供的不能满足要求，可以通过DateTimeFormatter的ofPattern方法创建自定义格式化方式\r\n\r\n```java\r\nLocalDate localDate = LocalDate.of(2019, 9, 10);\r\nString s1 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);\r\nString s2 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);\r\n//自定义格式化\r\nDateTimeFormatter dateTimeFormatter =   DateTimeFormatter.ofPattern(\"dd/MM/yyyy\");\r\nString s3 = localDate.format(dateTimeFormatter);\r\n```\r\n\r\n　　**解析时间**。和SimpleDateFormat相比，DateTimeFormatter是线程安全的\r\n\r\n```java\r\nLocalDate localDate1 = LocalDate.parse(\"20190910\", DateTimeFormatter.BASIC_ISO_DATE);\r\nLocalDate localDate2 = LocalDate.parse(\"2019-09-10\", DateTimeFormatter.ISO_LOCAL_DATE);\r\n```\r\n\r\n　　**Date与LocalDateTime转换。**\r\n\r\n```java\r\n/**\r\n  * LocalDateTime转毫秒时间戳\r\n  * @param localDateTime LocalDateTime\r\n  * @return 时间戳\r\n  */\r\npublic static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {\r\n    try {\r\n        ZoneId zoneId = ZoneId.systemDefault();\r\n        Instant instant = localDateTime.atZone(zoneId).toInstant();\r\n        return instant.toEpochMilli();\r\n    } catch (Exception e) {\r\n        e.printStackTrace();\r\n    }\r\n    return null;\r\n}\r\n\r\n/**\r\n * 时间戳转LocalDateTime\r\n * @param timestamp 时间戳\r\n * @return LocalDateTime\r\n */\r\npublic static LocalDateTime timestampToLocalDateTime(long timestamp) {\r\n    try {\r\n        Instant instant = Instant.ofEpochMilli(timestamp);\r\n        ZoneId zone = ZoneId.systemDefault();\r\n        return LocalDateTime.ofInstant(instant, zone);\r\n    } catch (Exception e) {\r\n        e.printStackTrace();\r\n    }\r\n    return null;\r\n}\r\n\r\n/**\r\n * Date转LocalDateTime\r\n * @param date Date\r\n * @return LocalDateTime\r\n */\r\npublic static LocalDateTime dateToLocalDateTime(Date date) {\r\n    try {\r\n        Instant instant = date.toInstant();\r\n        ZoneId zoneId = ZoneId.systemDefault();\r\n        return instant.atZone(zoneId).toLocalDateTime();\r\n    } catch (Exception e) {\r\n        e.printStackTrace();\r\n    }\r\n    return null;\r\n}\r\n\r\n/**\r\n * LocalDateTime转Date\r\n * @param localDateTime LocalDateTime\r\n * @return Date\r\n */\r\npublic static Date localDateTimeToDate(LocalDateTime localDateTime) {\r\n    try {\r\n        ZoneId zoneId = ZoneId.systemDefault();\r\n        ZonedDateTime zdt = localDateTime.atZone(zoneId);\r\n        return Date.from(zdt.toInstant());\r\n    } catch (Exception e) {\r\n        e.printStackTrace();\r\n    }\r\n    return null;\r\n}\r\n```\r\n\r\n　　**SpringBoot中应用LocalDateTime**\r\n\r\n　　将LocalDateTime字段以时间戳的方式返回给前端 添加日期转化类\r\n\r\n```java\r\n public class LocalDateTimeConverter extends JsonSerializer<LocalDateTime> {\r\n        @Override\r\n        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {\r\n            gen.writeNumber(value.toInstant(ZoneOffset.of(\"+8\")).toEpochMilli());\r\n        }\r\n    }\r\n```\r\n\r\n　　并在LocalDateTime字段上添加**@JsonSerialize(using = LocalDateTimeConverter.class)**注解，如下：\r\n\r\n```java\r\n@JsonSerialize(using = LocalDateTimeConverter.class)\r\nprotected LocalDateTime gmtModified;\r\n```\r\n\r\n　　将**LocalDateTime**字段以指定格式化日期的方式返回给前端 在LocalDateTime字段上添加**@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=\"yyyy-MM-dd HH:mm:ss\")**注解即可，如下：\r\n\r\n```java\r\n@JsonFormat(shape=JsonFormat.Shape.STRING, pattern=\"yyyy-MM-dd HH:mm:ss\")\r\nprotected LocalDateTime gmtModified;\r\n```\r\n\r\n　　对前端传入的日期进行格式化 在**LocalDateTime**字段上添加**@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")**注解即可，如下：\r\n\r\n```java\r\n@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\r\nprotected LocalDateTime gmtModified;\r\n```\r\n\r\n　　**总结：**\r\n　　LocalDateTime：Date有的我都有，Date没有的我也有，','2020-09-14 10:29:55.785000','https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1600089378984&di=3ad35394a6976a04703c1e972f835e6d&imgtype=0&src=http%3A%2F%2Ffeng-bbs-att-1255531212.image.myqcloud.com%2F2020%2F08%2F16%2F1605367e4kae8k6z7rwmf5.jpeg','转载',1,1,'JDK8的LocalDateTime用法','2020-09-14 10:29:55.785000',2,24,1,'10,15','JDK8新特性里提供了3个时间类：LocalDate、LocalTime、LocalDateTime，在项目开发中，需要对Date类型进行格式，格式化Date类型要使用SimpleDateFormat，但SimpleDateFormat是现成不安全的。'),
(37,1,'## 1、注册拦截器\r\n```java\r\n/**\r\n* 注册拦截器\r\n*/\r\n@Configuration\r\npublic class WebConfigurer implements WebMvcConfigurer {\r\n\r\n  @Autowired\r\n  private LoginInterceptor loginHandlerInterceptor;\r\n\r\n  @Override\r\n  public void addInterceptors(InterceptorRegistry registry) {\r\n   InterceptorRegistration ir = registry.addInterceptor(loginHandlerInterceptor);\r\n   // 拦截路径\r\n   ir.addPathPatterns(\"/*\");\r\n   // 不拦截路径\r\n   List<String> irs = new ArrayList<String>();\r\n   irs.add(\"/api/*\");\r\n   irs.add(\"/wechat/*\");\r\n   irs.add(\"/oauth\");\r\n   ir.excludePathPatterns(irs);\r\n  }\r\n}\r\n```\r\n\r\n## 2、登录拦截\r\n```java\r\n* 未登录拦截器\r\n*/\r\n@Component\r\npublic class LoginInterceptor implements HandlerInterceptor {\r\n\r\n  @Autowired\r\n  private LoginDao dao;\r\n\r\n  @Override\r\n  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {\r\n   // 获得cookie\r\n   Cookie[] cookies = request.getCookies();\r\n   // 没有cookie信息，则重定向到登录界面\r\n   if (null == cookies) {\r\n   response.sendRedirect(request.getContextPath() + \"/login\");\r\n   return false;\r\n   }\r\n   // 定义cookie_username，用户的一些登录信息，例如：用户名，密码等\r\n   String cookie_username = null;\r\n   // 获取cookie里面的一些用户信息\r\n   for (Cookie item : cookies) {\r\n   if (\"cookie_username\".equals(item.getName())) {\r\n   cookie_username = item.getValue();\r\n   break;\r\n   }\r\n   }\r\n   // 如果cookie里面没有包含用户的一些登录信息，则重定向到登录界面\r\n   if (StringUtils.isEmpty(cookie_username)) {\r\n   response.sendRedirect(request.getContextPath() + \"/login\");\r\n   return false;\r\n  }\r\n   // 获取HttpSession对象\r\n   HttpSession session = request.getSession();\r\n   // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期\r\n   Object obj = session.getAttribute(Const.SYSTEM_USER_SESSION);\r\n   if (null == obj) {\r\n   // 根据用户登录账号获取数据库中的用户信息\r\n   UserInfo dbUser = dao.getUserInfoByAccount(cookie_username);\r\n   // 将用户保存到session中\r\n   session.setAttribute(Const.SYSTEM_USER_SESSION, dbUser);\r\n   }\r\n   // 已经登录\r\n   return true;\r\n  }\r\n}\r\n```\r\n\r\n## 3、登录请求\r\n**控制层**\r\n```java\r\n/**\r\n * 执行登录\r\n */\r\n @PostMapping(\"login\")\r\n @ResponseBody\r\n public String login(String username, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {\r\n   return service.doLogin(username.trim(), password.trim(), session, request, response).toJSONString();\r\n }\r\n```\r\n**业务层**\r\n```java\r\n/**\r\n * 执行登录\r\n */\r\npublic JSONObject doLogin(String username, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) {\r\n   // 最终返回的对象\r\n  JSONObject res = new JSONObject();\r\n  res.put(\"code\", 0);\r\n  if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {\r\n   res.put(\"msg\", \"请输入手机号或密码\");\r\n   return res;\r\n  }\r\n  UserInfo dbUser = dao.getUserInfoByAccount(username);\r\n  if (null == dbUser) {\r\n   res.put(\"msg\", \"该账号不存在，请检查后重试\");\r\n   return res;\r\n  }\r\n  // 验证密码是否正确\r\n  String newPassword = PasswordUtils.getMd5(password, username, dbUser.getSalt());\r\n  if (!newPassword.equals(dbUser.getPassword())) {\r\n   res.put(\"msg\", \"手机号或密码错误，请检查后重试\");\r\n   return res;\r\n  }\r\n  // 判断账户状态\r\n  if (1 != dbUser.getStatus()) {\r\n   res.put(\"msg\", \"该账号已被冻结，请联系管理员\");\r\n   return res;\r\n  }\r\n  // 将登录用户信息保存到session中\r\n  session.setAttribute(Const.SYSTEM_USER_SESSION, dbUser);\r\n  // 保存cookie，实现自动登录\r\n  Cookie cookie_username = new Cookie(\"cookie_username\", username);\r\n  // 设置cookie的持久化时间，30天\r\n  cookie_username.setMaxAge(30 * 24 * 60 * 60);\r\n  // 设置为当前项目下都携带这个cookie\r\n  cookie_username.setPath(request.getContextPath());\r\n  // 向客户端发送cookie\r\n  response.addCookie(cookie_username);\r\n  res.put(\"code\", 1);\r\n  res.put(\"msg\", \"登录成功\");\r\n  return res;\r\n}\r\n```\r\n## 4、注销登录\r\n```java\r\n/**\r\n * 退出登录\r\n */\r\n@RequestMapping(value = \"logout\")\r\npublic String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {\r\n  // 删除session里面的用户信息\r\n  session.removeAttribute(Const.SYSTEM_USER_SESSION);\r\n  // 保存cookie，实现自动登录\r\n  Cookie cookie_username = new Cookie(\"cookie_username\", \"\");\r\n  // 设置cookie的持久化时间，0\r\n  cookie_username.setMaxAge(0);\r\n  // 设置为当前项目下都携带这个cookie\r\n  cookie_username.setPath(request.getContextPath());\r\n  // 向客户端发送cookie\r\n  response.addCookie(cookie_username);\r\n  return \"login\";\r\n}\r\n```\r\n注销登录时，我们需要删除 session 里面的用户信息，删除 cookie 里面的用户信息，然后请求到登录界面','2020-09-14 10:40:23.259000','http://images.jasonpang.top/%E7%8E%89%E5%AD%903.jpg','原创',1,1,'SpringBoot中使用Cookie实现记住登录的示例代码','2020-09-14 10:40:23.259000',6,24,1,'10,15','SpringBoot 中使用 Cookie 实现记住登录功能，在项目中还算是比较实用的功能，希望能对正在阅读的你一点点帮助和启发');

/*Table structure for table `t_blog_tags` */

DROP TABLE IF EXISTS `t_blog_tags`;

CREATE TABLE `t_blog_tags` (
  `blogsId` bigint(20) NOT NULL,
  `tagsId` bigint(20) NOT NULL,
  KEY `FK5feau0gb4lq47fdb03uboswm8` (`tagsId`),
  KEY `FKh4pacwjwofrugxa9hpwaxg6mr` (`blogsId`),
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tagsId`) REFERENCES `t_tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogsId`) REFERENCES `t_blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_blog_tags` */

insert  into `t_blog_tags`(`blogsId`,`tagsId`) values 
(26,10),
(26,15),
(27,11),
(27,12),
(27,14),
(28,10),
(28,15),
(28,16),
(28,17),
(29,10),
(29,13),
(29,15),
(30,10),
(31,10),
(32,19),
(33,19),
(34,14),
(35,11),
(36,10),
(36,15),
(37,10),
(37,15);

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `blog_id` bigint(20) DEFAULT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  `ReplyToUser` varchar(20) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`),
  KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`content`,`create_time`,`email`,`nickname`,`blog_id`,`parent_comment_id`,`ReplyToUser`,`flag`) values 
(17,'测试评论功能','2020-09-14 11:18:15.740000','a1923517480@gmail.com','d1',26,NULL,'',0),
(18,'测试评论功能','2020-09-14 11:19:58.820000','a1923517480@gmail.com','d2',26,17,'@d1',0),
(22,'管理员回复你了','2020-09-14 13:21:28.259000','','管理员',26,20,'@12312',1),
(24,'测试评论功能2','2020-09-14 14:36:16.802000','','管理员',26,NULL,'',1),
(27,'狗管理爬爬爬','2020-09-15 01:51:18.746000','1923517480@qq.com','狗管理',26,NULL,'',0),
(28,'博主6666','2020-09-15 04:32:14.302000','','水饺皮',26,NULL,'',1),
(29,'博主6666傻逼','2020-09-15 04:32:27.897000','','水饺皮',26,27,'@狗管理',1),
(32,'6666','2020-09-15 06:54:23.859000','666@qq.ccc','666',30,NULL,'',0),
(33,'6666','2020-09-17 04:35:57.129000','666@qq.ccc','6666',26,24,'@管理员',0),
(35,'给个赞','2020-09-17 05:14:39.186000','1923517480@qq.com','路人AA',26,NULL,'',0),
(37,'1231','2020-09-18 16:10:52.136000','','水饺皮',26,35,'@路人AA',1),
(38,'ce','2020-09-19 10:06:00.751000','','水饺皮',26,NULL,'',1);

/*Table structure for table `t_comment_html` */

DROP TABLE IF EXISTS `t_comment_html`;

CREATE TABLE `t_comment_html` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  `ReplyToUser` varchar(20) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `t_comment_html` */

insert  into `t_comment_html`(`id`,`content`,`create_time`,`email`,`nickname`,`parent_comment_id`,`ReplyToUser`,`flag`) values 
(18,'测试评论功能','2020-09-14 11:19:58.820000','a1923517480@gmail.com','d2',17,'@d1',0),
(22,'管理员回复你了','2020-09-14 13:21:28.259000','','管理员',20,'@12312',1),
(29,'博主6666傻逼','2020-09-15 04:32:27.897000','','水饺皮',27,'@狗管理',1),
(33,'6666','2020-09-17 04:35:57.129000','666@qq.ccc','6666',24,'@管理员',0),
(37,'爱你摸摸大','2020-09-17 16:58:13.794000','1923517480@qq.com','爱你',35,'@路人AA',0),
(39,'这是我的个人网站，欢迎大家访问并留言，有bug请直接在留言区反馈~','2020-09-19 08:50:43.525000','','水饺皮',NULL,'',1);

/*Table structure for table `t_count` */

DROP TABLE IF EXISTS `t_count`;

CREATE TABLE `t_count` (
  `id` int(1) NOT NULL,
  `count` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_count` */

insert  into `t_count`(`id`,`count`) values 
(1,33);

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

insert  into `t_tag`(`id`,`name`) values 
(10,'Java'),
(11,'JavaScript'),
(12,'AJAX'),
(13,'JSON'),
(14,'JQuery'),
(15,'Spring'),
(16,'MyBatis'),
(17,'MySQL'),
(18,'CSS'),
(19,'Vue'),
(20,'Maven'),
(21,'游戏');

/*Table structure for table `t_type` */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `t_type` */

insert  into `t_type`(`id`,`name`) values 
(24,'Java'),
(31,'前端'),
(32,'后端'),
(33,'框架'),
(34,'数据库'),
(35,'学习'),
(36,'钻研'),
(37,'异常处理'),
(38,'学习日记'),
(39,'兴趣爱好');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`avatar`,`create_time`,`email`,`nickname`,`password`,`type`,`update_time`,`username`) values 
(1,'../static/imges/touxiang.png',NULL,NULL,'水饺皮','11d5279a07650dfbf74ded54c5795d5f',NULL,NULL,'yokiua');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
