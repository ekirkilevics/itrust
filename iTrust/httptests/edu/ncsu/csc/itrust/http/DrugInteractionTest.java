package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class DrugInteractionTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
	}

	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Edit Interactions"
	 * Choose "Adefovir" as one drug
	 * Choose "Aspirin" as the other drug
	 * Enter "May increase the risk and severity of nephrotoxicity due to additive effects on the kidney."
	 * Click submit
	 */
	public void testRecordDrugInteraction() throws Exception {
		gen.ndCodes1();
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		// click on Add HCP
		wr = wr.getLinkWith("Edit ND Codes").click();
		// add the hcp
		assertEquals("iTrust - Maintain ND Codes", wr.getTitle());
		wr = wr.getLinkWith("Edit Interactions").click();
		assertEquals("iTrust - Edit ND Code Interactions", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.getScriptableObject().setParameterValue("drug1", "61958-0501 Adefovir");
		form.getScriptableObject().setParameterValue("drug2", "08109-6 Aspirin");
		form.getScriptableObject().setParameterValue("description", "May increase the risk and severity of nephrotoxicity due to additive effects on the kidney.");
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Interaction recorded successfully"));
		
		
		
	}
	
	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Tetracycline"
	 * Choose "Isotretinoin" interaction
	 * Click delete
	 */
	public void testDeleteDrugInteraction() throws Exception {
		gen.ndCodes2();
		gen.drugInteractions();
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		// click on Edit ND Codes
		wr = wr.getLinkWith("Edit ND Codes").click();
		assertEquals("iTrust - Maintain ND Codes", wr.getTitle());
		// Click Tetracycline
		wr = wr.getLinkWith("Tetracycline").click();
		assertTrue(wr.getText().contains("May increase the risk of pseudotumor cerebri, or benign intracranial hypertension"));
		
		WebForm form = wr.getForms()[0];
		form.getScriptableObject().setParameterValue("codeToDelete", "548680955");
		form.getSubmitButton("delete").click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Interaction deleted successfully"));
		
	}
	
	/*
	 * Authenticate admin 90000000001
	 * Choose "Edit ND Codes"
	 * Choose "Edit Interactions"
	 * Choose "Adefovir" as both drug1 and drug2
	 * Enter "Mixing this drug with itself will cause the person taking it to implode."
	 * Click submit
	 */
	public void testRecordDrugInteractionSameDrugs() throws Exception {
		gen.ndCodes1();
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		// click on Add HCP
		wr = wr.getLinkWith("Edit ND Codes").click();
		// add the hcp
		assertEquals("iTrust - Maintain ND Codes", wr.getTitle());
		wr = wr.getLinkWith("Edit Interactions").click();
		assertEquals("iTrust - Edit ND Code Interactions", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.getScriptableObject().setParameterValue("drug1", "61958-0501 Adefovir");
		form.getScriptableObject().setParameterValue("drug2", "61958-0501 Adefovir");
		form.getScriptableObject().setParameterValue("description", "Mixing this drug with itself will cause the person taking it to implode.");
		form.getButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Interactions can only be recorded between two different drugs"));
	}
	
}