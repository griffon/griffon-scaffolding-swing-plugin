package griffon.plugins.scaffolding.templates

def valueHolder = scaffoldingContext.validateable."${propertyName}Property"()

Map widgetAttributes = scaffoldingContext.widgetAttributes('checkBox', constrainedProperty)
if (!widgetAttributes.containsKey('constraints')) widgetAttributes.constraints = 'top, grow'
if (valueHolder.value != null) attributesCopy.selected = valueHolder.value

errorDecorator {
    checkBox(widgetAttributes)
    scaffoldingContext.bind(getVariable(propertyName), 'selected',
        valueHolder, constrainedProperty)
}