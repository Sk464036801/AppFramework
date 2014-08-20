/*
 * MibTypeSymbol.java
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

/**
 * A MIB type symbol. This class holds information relevant to a MIB
 * type assignment, i.e. a defined type name.
 *
 * @author   Per Cederberg, <per at percederberg dot net>
 * @version  2.2
 * @since    2.0
 */
public class MibTypeSymbol extends MibSymbol {

    /**
     * The symbol type.
     */
    private MibType type;

    /**
     * Creates a new type symbol
     *
     * @param location       the symbol location
     * @param mib            the symbol MIB file
     * @param name           the symbol name
     * @param type           the symbol type
     *
     * @since 2.2
     */
    MibTypeSymbol(FileLocation location,
                  Mib mib,
                  String name,
                  MibType type) {

        super(location, mib, name);
        this.type = type;
    }

    /**
     * Initializes the MIB symbol. This will remove all levels of
     * indirection present, such as references to types or values. No
     * information is lost by this operation. This method may modify
     * this object as a side-effect.<p>
     *
     * <strong>NOTE:</strong> This is an internal method that should
     * only be called by the MIB loader.
     *
     * @param log            the MIB loader log
     *
     * @throws net.percederberg.mibble.MibException if an error was encountered during the
     *             initialization
     */
    public void initialize(MibLoaderLog log) throws MibException {
        if (type != null) {
            type = type.initialize(this, log);
        }
    }

    /**
     * Returns the symbol type.
     *
     * @return the symbol type
     */
    public MibType getType() {
        return type;
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object
     */
    public String toString() {
        StringBuffer  buffer = new StringBuffer();

        buffer.append("TYPE ");
        buffer.append(getName());
        buffer.append(" ::= ");
        buffer.append(getType());
        return buffer.toString();
    }
}
