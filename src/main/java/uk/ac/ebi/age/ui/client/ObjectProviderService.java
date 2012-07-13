package uk.ac.ebi.age.ui.client;

import uk.ac.ebi.age.ui.shared.imprint.ObjectId;
import uk.ac.ebi.age.ui.shared.imprint.ObjectImprint;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ObjectProviderService
{
 void getObject( ObjectId id, AsyncCallback<ObjectImprint> cb );
}
