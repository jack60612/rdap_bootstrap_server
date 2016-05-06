/*
 * Copyright (C) 2013-2016 American Registry for Internet Numbers (ARIN)
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
 *
 */
package net.arin.rdap_bootstrap.service;

import net.arin.rdap_bootstrap.format.Rfc7484;
import net.arin.rdap_bootstrap.lookup.AsEMap;
import net.arin.rdap_bootstrap.lookup.Lookup;
import net.arin.rdap_bootstrap.lookup.ServiceUrls;
import net.arin.rdap_bootstrap.service.ResourceFiles.BootFiles;
import net.ripe.ipresource.IpResource;

/**
 * @version $Rev$, $Date$
 */
public class AsBootstrap implements Bootstrap, Lookup.As, Rfc7484.Handler
{

    private volatile AsEMap allocations = new AsEMap();
    private AsEMap _allocations;

    private ServiceUrls serviceUrls;
    private String publication;
    private String description;

    @Override
    public void startServices()
    {
        _allocations = new AsEMap();
    }

    @Override
    public void endServices()
    {
        allocations = _allocations;
    }

    @Override
    public void startService()
    {
        serviceUrls = new ServiceUrls();
    }

    @Override
    public void endService()
    {
        //nothing to do
    }

    @Override
    public void addServiceEntry( String entry )
    {
        _allocations.store( IpResource.parse( entry ), serviceUrls );
    }

    @Override
    public void addServiceUrl( String url )
    {
        serviceUrls.addUrl( url );
    }

    public void loadData( ResourceFiles resourceFiles )
        throws Exception
    {
        Rfc7484 bsFile = new Rfc7484();
        bsFile.loadData( resourceFiles.getInputStream( BootFiles.AS.getKey() ), this );
    }

    public ServiceUrls getServiceUrlsForAs( IpResource ipResource )
    {
        return allocations.getServiceUrlsForAs( ipResource );
    }

    @Override
    public void setPublication( String publication ) { this.publication = publication; }
    public String getPublication() { return publication; }

    public String getDescription()
    {
        return description;
    }

    @Override
    public void setDescription( String description )
    {
        this.description = description;
    }
}
