/*
 * Copyright 2009-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package griffon.plugins.scaffolding.factories

import griffon.jxlayer.factory.JXLayerFactory
import griffon.plugins.scaffolding.decorators.ErrorDecorator
import griffon.plugins.scaffolding.decorators.IconErrorDecorator
import griffon.plugins.scaffolding.decorators.TranslucentErrorDecorator
import griffon.plugins.scaffolding.nodes.CompositeLayerUI

/**
 * @author Andres Almiray
 */
class ErrorDecoratorFactory extends JXLayerFactory {
    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Object node = super.newInstance(builder, name, value, attributes)

        builder.context.decorators = attributes.remove('decorators')

        node
    }

    @Override
    void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object node) {
        List<ErrorDecorator> decorators = []
        if (builder.context.decorators) {
            builder.context.decorators.split(',').collect(decorators) { String decorator ->
                decorator = decorator.trim()
                switch (decorator) {
                    case 'icon': return new IconErrorDecorator()
                    case 'mask': return new TranslucentErrorDecorator()
                    default:
                        Class decoratorClass = ApplicationClassLoader.get().loadClass(decorator)
                        return (ErrorDecorator) decoratorClass.newInstance()
                }
            }
        }

        if (!decorators) decorators << new IconErrorDecorator()

        node.setUI(new CompositeLayerUI(
            builder.getVariable('scaffoldingContext'),
            builder.getVariable('constrainedProperty'),
            decorators
        ))

        super.onNodeCompleted(builder, parent, node)
    }
}
