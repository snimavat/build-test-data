import grails.buildtestdata.UnitTestDataBuilder

trait DomainTestDataServiceBase implements DomainTestHelper,UnitTestDataBuilder {

    Class createDomainClass(String classText) {
        Class domainClass = setUpDomainClass(classText)
        mockDomain(domainClass)
        domainClass
    }

}
