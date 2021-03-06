package grails.buildtestdata.handler

import grails.buildtestdata.CircularCheckList
import grails.gorm.validation.ConstrainedProperty
import grails.gorm.validation.Constraint
import groovy.transform.CompileStatic
import org.grails.datastore.gorm.GormEntity
import org.grails.datastore.gorm.validation.constraints.SizeConstraint

@CompileStatic
class SizeConstraintHandler extends AbstractConstraintHandler {
    @Override
    void handle(GormEntity domain, String propertyName, Constraint appliedConstraint, ConstrainedProperty constrainedProperty, CircularCheckList circularCheckList) {
        SizeConstraint sizeConstraint = appliedConstraint as SizeConstraint
        IntRange range = sizeConstraint.range

        MinSizeConstraintHandler.padMinSize(
            domain,
            constrainedProperty.appliedConstraints,
            propertyName,
            getProperty(domain, propertyName),
            range.from,
            circularCheckList
        )

        MaxSizeConstraintHandler.padMaxSize(domain, propertyName, getProperty(domain, propertyName), range.to)
    }
}
