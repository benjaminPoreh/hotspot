

package meetup;

import java.text.SimpleDateFormat;
import java.util.*;

import meetup.internal.xstream.IntConverter;

public class ActivitySearchCriteria
{
        private String memberId;
        private String pageStart; // pageStart is a timestamp value

        public String getMemberId()
        {
                return memberId;
        }



        public void setMemberId(String memberId)
        {
                this.memberId = memberId;
        }



        public String getPageStart()
        {
                return pageStart;
        }



        public void setPageStart(Integer page)
        {
                this.pageStart = "" + page;
        }
        
        public void setPageStart(String pageStart)
        {
                this.pageStart = pageStart;
        }



        public String toString()
        {
                return IntConverter.build(this);
        }



        public Map<String, String> getParameterMap()
        {
                Map<String, String> m = new HashMap<String, String>();
                
                if (this.getMemberId() != null)
                {
                        m.put("member_id", getMemberId());
                }
                
                if (this.getPageStart() != null)
                {
                        m.put("page_start", getPageStart());
                }
                
                return m;
        }
}