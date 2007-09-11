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
package org.apache.sling.assembly.internal;

import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.sling.assembly.installer.BundleRepositoryAdmin;
import org.osgi.framework.Version;
import org.osgi.service.obr.Repository;
import org.osgi.service.obr.RepositoryAdmin;
import org.osgi.service.obr.Resource;


/**
 * The <code>BundleRepositoryAdminImpl</code> TODO
 */
class BundleRepositoryAdminImpl implements BundleRepositoryAdmin {

    private InstallerServiceImpl installerService;

    BundleRepositoryAdminImpl(InstallerServiceImpl installerService) {
        this.installerService = installerService;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.sling.core.assembly.installer.BundleRepositoryAdmin#addRepository(java.net.URL)
     */
    public void addRepository(URL url) {
        Object lock = installerService.acquireLock(0);
        try {
            getRepositoryAdmin().addRepository(url);
        } catch (Exception e) {
            // TODO: log
        } finally {
            installerService.releaseLock(lock);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.sling.core.assembly.installer.BundleRepositoryAdmin#getRepositories()
     */
    public Iterator getRepositories() {
        Object lock = installerService.acquireLock(0);
        try {
            Repository[] repos = getRepositoryAdmin().listRepositories();
            if (repos == null || repos.length == 0) {
                return Collections.EMPTY_LIST.iterator();
            }

            SortedSet urlSet = new TreeSet();
            for (int i = 0; i < repos.length; i++) {
                urlSet.add(new RepositoryImpl(repos[i]));
            }
            return urlSet.iterator();
        } finally {
            installerService.releaseLock(lock);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.sling.core.assembly.installer.BundleRepositoryAdmin#getResources()
     */
    public Iterator getResources() {
        Object lock = installerService.acquireLock(0);
        try {
            Repository[] repos = getRepositoryAdmin().listRepositories();
            if (repos == null || repos.length == 0) {
                return Collections.EMPTY_LIST.iterator();
            }

            SortedSet resSet = new TreeSet();
            for (int i = 0; i < repos.length; i++) {
                Resource[] resources = repos[i].getResources();
                if (resources.length > 0) {
                    for (int j = 0; resources != null && j < resources.length; j++) {
                        resSet.add(new ResourceImpl(resources[j]));
                    }
                }
            }
            return resSet.iterator();
        } finally {
            installerService.releaseLock(lock);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.sling.core.assembly.installer.BundleRepositoryAdmin#refreshRepositories()
     */
    public void refreshRepositories() {
        // note: refreshing is implemented by re-adding the repositories
        Object lock = installerService.acquireLock(0);
        try {
            Repository[] repos = getRepositoryAdmin().listRepositories();
            for (int i = 0; repos != null && i < repos.length; i++) {
                addRepository(repos[i].getURL());
            }
        } finally {
            installerService.releaseLock(lock);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.sling.core.assembly.installer.BundleRepositoryAdmin#removeRepository(java.net.URL)
     */
    public void removeRepository(URL url) {
        Object lock = installerService.acquireLock(0);
        try {
            getRepositoryAdmin().removeRepository(url);
        } finally {
            installerService.releaseLock(lock);
        }
    }

    // ---------- internal class -----------------------------------------------

    private RepositoryAdmin getRepositoryAdmin() {
        return installerService.getRepositoryAdmin();
    }

    // ---------- internal classes ---------------------------------------------

    private static class ResourceImpl implements
            org.apache.sling.assembly.installer.Resource, Comparable {

        private final Resource delegatee;

        ResourceImpl(Resource delegatee) {
            this.delegatee = delegatee;
        }

        public String getPresentationName() {
            return delegatee.getPresentationName();
        }

        public String getSymbolicName() {
            return delegatee.getSymbolicName();
        }

        public Version getVersion() {
            return delegatee.getVersion();
        }

        public String[] getCategories() {
            return delegatee.getCategories();
        }

        // ---------- Comparable -----------------------------------------------

        public int compareTo(Object obj) {
            if (this == obj) {
                return 0;
            }

            // ClassCastException is allowed to be thrown here
            ResourceImpl other = (ResourceImpl) obj;

            if (getSymbolicName().equals(other.getSymbolicName())) {
                return getVersion().compareTo(other.getVersion());
            }

            return getSymbolicName().compareTo(other.getSymbolicName());
        }

        // ---------- Object overwrite -----------------------------------------

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof ResourceImpl) {
                ResourceImpl other = (ResourceImpl) obj;
                return getSymbolicName().equals(other.getSymbolicName())
                    && getVersion().equals(other.getVersion());
            }

            return false;
        }

        public int hashCode() {
            return getSymbolicName().hashCode() * 17 + getVersion().hashCode()
                * 33;
        }
    }

    private static class RepositoryImpl implements
            org.apache.sling.assembly.installer.Repository, Comparable {

        private final Repository delegatee;

        RepositoryImpl(Repository delegatee) {
            this.delegatee = delegatee;
        }

        public String getName() {
            return delegatee.getName();
        }

        public long getLastModified() {
            return delegatee.getLastModified();
        }

        public URL getURL() {
            return delegatee.getURL();
        }

        // ---------- Comparable -----------------------------------------------

        public int compareTo(Object obj) {
            if (this == obj) {
                return 0;
            }

            // ClassCastException is allowed to be thrown here
            RepositoryImpl other = (RepositoryImpl) obj;

            if (getName().equals(other.getName())) {
                return getURL().toString().compareTo(other.getURL().toString());
            }

            return getName().compareTo(other.getName());
        }

        // ---------- Object overwrite -----------------------------------------

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj instanceof RepositoryImpl) {
                RepositoryImpl other = (RepositoryImpl) obj;
                return getURL().equals(other.getURL());
            }

            return false;
        }

        public int hashCode() {
            return getURL().hashCode();
        }
    }
}
