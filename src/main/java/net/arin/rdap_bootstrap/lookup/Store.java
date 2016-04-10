/*
 * Copyright (C) 2016 American Registry for Internet Numbers (ARIN)
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package net.arin.rdap_bootstrap.lookup;

import com.googlecode.ipv6.IPv6Network;
import net.ripe.ipresource.IpRange;
import net.ripe.ipresource.IpResource;

/**
 * Defines the interfaces for storing data.
 */
public interface Store
{

    interface As
    {
        void store( AsRangeInfo asRangeInfo );
    }

    interface Domain
    {
        void store( String domain, ServiceUrls serviceUrls );
    }

    interface Entity
    {
        void store( String entity, ServiceUrls serviceUrls );
    }

    interface IpV4
    {
        void store( IpResource ipResource, ServiceUrls serviceUrls );
    }

    interface IpV6
    {
        void store( IPv6Network iPv6Network, ServiceUrls serviceUrls );
    }
}
