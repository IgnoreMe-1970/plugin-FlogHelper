/* This code is part of Freenet. It is distributed under the GNU General
 * Public License, version 2 (or at your option any later version). See
 * http://www.gnu.org/ for further details of the GPL. */
package plugins.floghelper.ui;

import freenet.client.HighLevelSimpleClient;
import freenet.clients.http.PageMaker;
import freenet.clients.http.Toadlet;
import freenet.clients.http.ToadletContext;
import freenet.clients.http.ToadletContextClosedException;
import freenet.pluginmanager.PluginStore;
import freenet.support.api.HTTPRequest;
import java.io.IOException;
import java.net.URI;
import plugins.floghelper.FlogHelper;

/**
 *
 * @author romain
 */
public abstract class FlogHelperToadlet extends Toadlet {

	public static final String BASE_URI = "/floghelper";
	private final String path;

	public FlogHelperToadlet(HighLevelSimpleClient hlsc, String path) {
		super(hlsc);
		this.path = path;
	}

	@Override
	public String path() {
		return BASE_URI + this.path;
	}

	public String getURIArgument(HTTPRequest request) {
		return request.getPath().substring(this.path().length()).split("\\?")[0];
	}

	public PageMaker getPM() {
		return FlogHelper.getPR().getPageMaker();
	}

	public PluginStore getFlogID(HTTPRequest request) {
		if (FlogHelper.getStore().subStores.containsKey(this.getURIArgument(request))) {
			return FlogHelper.getStore().subStores.get(this.getURIArgument(request));
		} else if (request.isPartSet("FlogID") && FlogHelper.getStore().subStores.containsKey(request.getPartAsString("FlogID", 7))) {
			return FlogHelper.getStore().subStores.get(request.getPartAsString("FlogID", 7));
		} else {
			return null;
		}
	}

	public abstract void handleMethodGET(URI uri,
			final HTTPRequest request, final ToadletContext ctx)
			throws ToadletContextClosedException, IOException;

	public abstract void handleMethodPOST(URI uri,
			HTTPRequest request, final ToadletContext ctx)
			throws ToadletContextClosedException, IOException;
}
