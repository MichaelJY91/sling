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

import org.apache.sling.core.content.SelectableSimpleContent;

/**
 * The <code>HierarchyContent</code> class is an abstract base content class
 * for content loaded from nodes of (extensions of) node type
 * <code>nt:HierarchyNode</code>.
 * 
 * @ocm.mapped jcrNodeType="nt:hierarchyNode" discriminator="false"
 */
public abstract class HierarchyContent extends SelectableSimpleContent {

    /** @ocm.field jcrName="jcr:created" */
    private long creationTime;

    // ---------- Mapped Content -----------------------------------------------

    public long getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }
}
