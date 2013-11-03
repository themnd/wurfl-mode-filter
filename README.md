## WURFL MODE FILTER

Changes to be done in greenfield online pom.xml:

add the property:

```xml
<commons-collections.version>3.2.1</commons-collections.version>
```

in the "modules" section add the wurl-mode-filter:

```xml
<modules>
	....
	<module>wurfl-mode-filter</module>
</modules>
```

in the "dependencyManagement" section change the version of the common collection:

```xml
<dependencyManagement>
	<dependencies>
		...
      <dependency>
        <groupId>commons-collections</groupId>
        <artifactId>commons-collections</artifactId>
        <version>${commons-collections.version}</version>
      </dependency>
      ...		
	</dependencies>
</dependencyManagement>
```

This is due to the use of ehcache 2.5.2 which require a newer version of commons-collections.

In the "depedencies" section add the plugin:

```xml
<dependencies>

	<dependency>
	  <groupId>com.atex.milan.plugins</groupId>
	  <artifactId>wurfl-mode-filter</artifactId>
	  <version>1.0-SNAPSHOT</version>
	</dependency>

</dependencies>
```

### module-greenfield

Since this module use "com.octo.captcha" and this is using an older version of ehcache, you should exclude it:

```xml
    <dependency>
      <groupId>com.octo.captcha</groupId>
      <artifactId>jcaptcha-all</artifactId>
      <version>1.0-RC6</version>
      <exclusions>
        <exclusion>
          <groupId>commons-logging</groupId>
          <artifactId>commons-logging</artifactId>
        </exclusion>
        <exclusion>
          <groupId>org.springframework</groupId>
          <artifactId>spring</artifactId>
        </exclusion>
        <exclusion>
          <groupId>net.sf.ehcache</groupId>
          <artifactId>ehcache</artifactId>
        </exclusion>
      </exclusions>
    </dependency>
```

#### webdispatcher e webfront

In the web.xml of the webapps replace com.polopoly.siteengine.dispatcher.mode.ModeUrlTranslatorFilter with com.atex.milan.plugins.wmodefilter.filter.WURFLFilter:

```xml
  <filter>
    <filter-name>modefilter</filter-name>
    <filter-class>com.atex.milan.plugins.wmodefilter.filter.WURFLFilter</filter-class>
  </filter>
```

## Maven settings.xml

You must add these two servers with your current polopoly username and password to maven settings.xml:

```xml
    <server>
      <id>atex-milan-public</id>
      <username>yourusername</username>
      <password>yourpassword</password>
    </server>
    <server>
      <id>atex-milan-snapshots</id>
      <username>yourusername</username>
      <password>yourpassword</password>
    </server>
```

### Contact

For information on this plugin, contact Marco Nova at mnova@atex.com.
