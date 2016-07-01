/*
 * Copyright (C) 2016-2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public License
 * version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package org.n52.tasking.data.sml.device;

import org.n52.tasking.data.sml.xml.SmlXPathConfig;
import org.n52.tasking.data.sml.xml.XPathParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SmlWriter {

    private final SmlParser smlParser;

    private final XPathParser xpathParser;

    public SmlWriter(SmlParser parser) {
        this.smlParser = parser;
        this.xpathParser = parser.getXPathParser();
    }

    void setParameterValue(String parameterName, Object d) {
        SmlXPathConfig config = smlParser.getSmlXPathConfig();
        final String xPath = config.getXPath("updatableParameter.node", parameterName);
        Node node = xpathParser.parseNode(xPath);
        if (node != null) {
//            xpathParser.parseNode(xPath, node)
            Node valueNode = xpathParser.parseNode("*/value", node);
            if (valueNode == null) {
                Document document = node.getOwnerDocument();
                valueNode = document.createElement("value");
                document.createTextNode(d.toString());
                node.appendChild(valueNode);
            } else {
                valueNode.setTextContent(d.toString());
            }
        }
    }
}
