package griffon.plugins.scaffolding.templates

Map widgetAttributes = scaffoldingContext.widgetAttributes('editorPane', constrainedProperty)
widgetAttributes.editable = constrainedProperty.editable
Map scrollPaneAttributes = widgetAttributes.remove('scrollPane') ?: [:]
scrollPaneAttributes.constraints = widgetAttributes.remove('constraints')

errorDecorator {
    scrollPane(scrollPaneAttributes) {
        editorPane(widgetAttributes)
        scaffoldingContext.bind(getVariable(propertyName), 'text',
            scaffoldingContext.validateable."${propertyName}Property"(), constrainedProperty)
    }
}