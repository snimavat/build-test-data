package base

import bookstore.Author
import grails.buildtestdata.UnitTestDataBuilder
import spock.lang.Specification

class BuildLazyUnitTests extends Specification implements UnitTestDataBuilder {
    void setup() {
        mockDomains(Author)
    }

    void testBuildLazyNoParamsCreatesNewWhenNoneExist() {
		assert Author.count() == 0

        when:
        def domainObject = buildLazy(Author)

        then:
        assert domainObject
		assert Author.count() == 1
    }

    void testBuildLazyNoParamsFindsExistingWithoutCreateNew() {
		build(Author, [firstName: "Foo", lastName: "Qux"])
		assert Author.count() == 1

        when:
        def domainObject = buildLazy(Author)

        then:
        assert domainObject
		assert domainObject.firstName == "Foo"
		assert domainObject.lastName == "Qux"
		assert Author.count() == 1
    }

    void testBuildLazyWithParamsCreatesNewWhenNoneExist() {
		assert Author.count() == 0

        when:
        def domainObject = buildLazy(Author, [firstName: "Bar"])

        then:
        assert domainObject
		assert Author.count() == 1
    }

    void testBuildLazyWithParamsCreatesNewWhenNoMatchingExist() {
		build(Author, [firstName: "Foo"])
		assert Author.count() == 1

        when:
        def domainObject = buildLazy(Author, [firstName: "Bar"])

        then:
        assert domainObject
		assert Author.count() == 2
    }

    void testBuildLazyWithParamsFindsExistingWithoutCreateNew() {
		build(Author, [firstName: "Foo", lastName: "Qux"])
		assert Author.count() == 1

        when:
        def domainObject = buildLazy(Author, [firstName: "Foo"])

        then:
        assert domainObject
		assert domainObject.firstName == "Foo"
		assert domainObject.lastName == "Qux"
		assert Author.count() == 1
    }

    void testBuildLazyWithParamsCreatesNewWithOnlyPartialMatch() {
		build(Author, [firstName: "Foo", lastName: "Qux"])
		assert Author.count() == 1

        when:
        def domainObject = buildLazy(Author, [firstName: "Foo", lastName: "Bar"])

        then:
        assert domainObject
		assert domainObject.firstName == "Foo"
		assert domainObject.lastName == "Bar"
		assert Author.count() == 2
    }
}
