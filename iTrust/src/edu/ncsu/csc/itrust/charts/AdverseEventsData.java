package edu.ncsu.csc.itrust.charts;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import edu.ncsu.csc.itrust.beans.AdverseEventBean;


public class AdverseEventsData implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, Serializable {
	
	/**
	 * The generated serializable ID.
	 */
	private static final long serialVersionUID = 6145689621506271656L;

	// Hardcoded months array to make implementation simpler
    private final String[] months = {"Jan","Feb","Mar","Apr","May","June","July","Aug","Sept","Oct","Nov","Dec"};
   
    // Initialize the values for each month to 0
    private int[] values = {0,0,0,0,0,0,0,0,0,0,0,0};
    
    // This will be the list of adverse events
    private List<AdverseEventBean> adverseEvents = new LinkedList<AdverseEventBean>();
    
    // This will be the name of the prescription or immunization under analysis
    private String codeName;
    
    public void setAdverseEventsList(List<AdverseEventBean> adEvents, String name)
    {
    	adverseEvents = adEvents;
    	this.codeName = name;
    }
    
	/**
	 *  Produces some random data.
	 */
    @SuppressWarnings("unchecked")
	public Object produceDataset(Map params) throws DatasetProduceException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(){
			/**
			 * The generated serializable ID.
			 */
			private static final long serialVersionUID = -8238489914590553747L;

			/**
			 * @see java.lang.Object#finalize()
			 */
			protected void finalize() throws Throwable {
				super.finalize();
			}
        };
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(AdverseEventBean event : adverseEvents)
        {
        	Calendar cal = Calendar.getInstance();
        	try {
				cal.setTime(sdf.parse(event.getDate()));
			} catch (ParseException e) {
				e.printStackTrace();
				throw new DatasetProduceException(e.getMessage());
			}
        	int monthOfReport = cal.get(Calendar.MONTH);
        	values[monthOfReport]++;
        }
        
        for(int i = 0; i < 12; i++)
        {
        	dataset.addValue(values[i],codeName,months[i]);
        }
          
        return dataset;
    }

    /**
     * This producer's data is invalidated after 5 seconds. By this method the
     * producer can influence Cewolf's caching behaviour the way it wants to.
     */
	@SuppressWarnings("unchecked")
	public boolean hasExpired(Map params, Date since) {		
		return (System.currentTimeMillis() - since.getTime())  > 5000;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	public String getProducerId() {
		return "AdverseEventsData DatasetProducer";
	}

    /**
     * Returns a link target for a special data item.
     */
    public String generateLink(Object data, int series, Object category) {
        return months[series];
    }
    
	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		return months[series];
	}

}
