/*
 * MibReference.java
 *
 * This work is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * This work is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Copyright (c) 2004 Per Cederberg. All rights reserved.
 */

package net.percederberg.mibble;

import java.util.ArrayList;

/**
 * A MIB file reference. This class references a MIB file that has
 * not yet been loaded.
 *
 * @author   Per Cederberg, <per at percederberg dot net>
 * @version  2.4
 * @since    2.0
 */
class MibReference implements MibContext {

    /**
     * The MIB loader being used.
     */
    private MibLoader loader;

    /**
     * The referenced MIB.
     */
    private Mib mib = null;

    /**
     * The reference location.
     */
    private FileLocation location;

    /**
     * The referenced MIB name.
     */
    private String name;

    /**
     * The referenced MIB symbol names.
     */
    private ArrayList symbols;

    /**
     * Creates a new MIB reference.
     *
     * @param loader         the MIB loader to use
     * @param location       the reference location
     * @param name           the referenced MIB name
     * @param symbols        the referenced MIB symbol names, or
     *                       null to allow references to any symbol
     */
    public MibReference(MibLoader loader,
                        FileLocation location,
                        String name,
                        ArrayList symbols) {

        this.loader = loader;
        this.location = location;
        this.name = name;
        this.symbols = symbols;
    }

    /**
     * Initializes the MIB reference. This will resolve all
     * referenced symbols in the MIB.  This method will be called by
     * the MIB loader.
     *
     * @param log            the MIB loader log
     *
     * @throws net.percederberg.mibble.MibException if an error was encountered during the
     *             initialization
     */
    public void initialize(MibLoaderLog log) throws MibException {
        String  message;

        mib = loader.getMib(name);
        if (mib == null) {
            message = "couldn't find referenced MIB '" + name + "'";
            throw new MibException(location, message);
        }
        if (symbols != null) {
            for (int i = 0; i < symbols.size(); i++) {
                if (mib.getSymbol(symbols.get(i).toString()) == null) {
                    message = "couldn't find imported symbol '" +
                              symbols.get(i) + "' in MIB '" + name + "'";
                    throw new MibException(location, message);
                }
            }
        }
    }

    /**
     * Returns the MIB name.
     *
     * @return the MIB name
     */
    public String getName() {
        return name;
    }

    /**
     * Searches for a named MIB symbol. This method may search outside
     * the normal (or strict) scope, thereby allowing a form of
     * relaxed search. Note that the results from the normal and
     * expanded search may not be identical, due to the context
     * chaining and the same symbol name appearing in various
     * contexts.<p>
     *
     * <strong>NOTE:</strong> This is an internal method that should
     * only be called by the MIB loader.
     *
     * @param name           the symbol name
     * @param expanded       the expanded scope flag
     *
     * @return the MIB symbol, or null if not found
     *
     * @since 2.4
     */
    public MibSymbol findSymbol(String name, boolean expanded) {
        if (mib == null) {
            return null;
        } else if (!expanded && symbols != null && !symbols.contains(name)) {
            return null;
        } else {
            return mib.getSymbol(name);
        }
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    public String toString() {
        return name;
    }
}
