## Greenfield Online

Changes to be done in greenfield online pom.xml:

add the property:

<commons-collections.version>3.2.1</commons-collections.version>

in the "modules" section add the wurl-mode-filter:

<modules>
	....
	<module>wurfl-mode-filter</module>
</modules>

in the "dependencyManagement" section change the version of the common collection:

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

This is due to the use of ehcache 2.5.2 which require a newer version of commons-collections.

In the "depedencies" section add the plugin:

<dependencies>

	<dependency>
	  <groupId>com.atex.milan.plugins</groupId>
	  <artifactId>wurfl-mode-filter</artifactId>
	  <version>1.0-SNAPSHOT</version>
	</dependency>

</dependencies>

### Branches

The Greenfield Online repository contains two types of branches, prefixed 'RELEASE' and 'RELENG'.

#### RELEASE branches

RELEASE branches contain released versions of Greenfield Online. Code on these branches depends on released versions of Polopoly.

#### RELENG branches

RELENG branches contain the latest Greenfield Online code for a particular version. Code on these branches usually depends on unreleased versions of Polopoly (using SNAPSHOT dependencies). At this time Polopoly does not distribute SNAPSHOT versions of our products, meaning it is not possible to build RELENG branches. It is still possible to use these branches to track changes.

### Issue handling

All Greenfield Online issue and support handling is performed in the [Greenfield Online](http://support.polopoly.com/jira/browse/GO "Greenfield Online") JIRA space on the support site.

### Contact

For information on the Greenfield Online repository, contact Polopoly Support at support.polopoly@atex.com.
