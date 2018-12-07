# Maven preparations

Make sure the following is present in mavens settings.xml: `~/.m2/settings.xml`

    <servers>
	  <server>
		<id>localhost</id>
		<username>admin</username>
		<password>admin</password>
	  </server>
	</servers>

To download all plugins and build all sub-projects:

    mvn install
    
# Tomcat preperations

Make sure the following is present in tomcat-users.xml: `%CATALINA_HOME%/conf/tomcat-users.xml`

    <tomcat-users>
	   <role rolename="manager-gui"/>
	   <role rolename="manager-script"/>
	   <user username="admin" password="admin" roles="manager-gui,manager-script" />
    </tomcat-users>