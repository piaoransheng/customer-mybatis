package com.lhc.customer.session;

import com.lhc.customer.Configuration;
import com.lhc.customer.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;


/**
 * 两个功能
 * <p>
 * 1.加载两类配置文件到configuration
 * 2.提供生成sqlsession的方法
 */
public class SqlSessionFactory {

    private final Configuration configuration = new Configuration();

    public SqlSessionFactory() {
        //1.加载配置信息
        loadDbInfo();
        //2.加载mapper.xml文件
        loadMappersInfo();
    }

    /**
     * 创建sqlSession
     *
     * @return
     */
    public SqlSession openSqlSession(){
        return new DefaultSqlSession(configuration);
    }

    /**
     * 加载数据库配置信息到configuration对象
     */
    private void loadDbInfo() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");  //配置文件的路径（在resources下）
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        configuration.setJdbcDriver(driver);
        configuration.setJdbcUrl(url);
        configuration.setJdbcUserName(username);
        configuration.setJdbcUserPassword(password);
    }

    /**
     * 加载所有mapper.xml信息到configuration对象
     */
    private void loadMappersInfo() {
        //mapper文件夹
        URL mapper = SqlSessionFactory.class.getClassLoader().getResource("mapper");
        File fileDirectory = new File(mapper.getFile());
        if (fileDirectory.isDirectory()) {
            //mapper文件夹下所有mapper.xml文件
            File[] mapperFiles = fileDirectory.listFiles();
            for (File file : mapperFiles) {
                //解析xml文件，把信息加载到configuration对象
                loadMapperInfo(file);
            }
        }
    }

    /**
     * 解析xml文件注册到configuration对象
     *
     * @param file 要被解析的文件
     */
    private void loadMapperInfo(File file) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点 <mapper>元素对象
        Element rootElement = document.getRootElement();
        //获取跟节点上的命名空间
        String namespace = rootElement.attribute("namespace").getData().toString();
        //获取所有子节点
        List<Element> selectList = rootElement.elements("select");
        //遍历所有子节点，将信息加载到mappedStatement对象，并等级到configuration对象
        for (Element element : selectList) {
            String id = element.attribute("id").getData().toString();
            String resultType = element.attribute("resultType").getData().toString();
            String sql = element.getData().toString();
            String sourceId = namespace + "." + id;

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setNameSpace(namespace);
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSql(sql);

            //注册到configuration对象
            configuration.getMappedStatementMap().put(sourceId, mappedStatement);
        }
    }
}