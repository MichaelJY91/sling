/*
 * Copyright 2007 The Apache Software Foundation.
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
package org.apache.sling.component.standard;

import javax.jcr.Value;

import org.apache.sling.core.content.SelectableSimpleContent;

/**
 * The <code>ResourceContent</code> TODO
 * 
 * @ocm.mapped jcrNodeType="nt:resource" discriminator="false"
 */
public class ResourceContent extends SelectableSimpleContent {

    /** @ocm.field jcrName="jcr:lastModified" */
    private long lastModificationTime;

    /** @ocm.field jcrName="jcr:mimeType" */
    private String mimeType;

    /** @ocm.field jcrName="jcr:encoding" */
    private String encoding;

    /** @ocm.field jcrName="jcr:data" */
    private Value value;

    // ---------- Mapped Content -----------------------------------------------

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the lastModificationTime
     */
    public long getLastModificationTime() {
        return lastModificationTime;
    }

    /**
     * @param lastModificationTime the lastModificationTime to set
     */
    public void setLastModificationTime(long lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the value
     */
    public Value getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Value value) {
        this.value = value;
    }

    // --------- Content interface ---------------------------------------------

    public String getComponentId() {
        return ResourceComponent.ID;
    }
}
