package shop.data;


/**
 * Implementation of Video interface.
 * @see Data
 */
final class VideoObj implements Video {
  private final String _title;
  private final int    _year;
  private final String _director;

  /**
   * Initialize all object attributes.
   */
  VideoObj(String title, int year, String director) {
    _title = title;
    _director = director;
    _year = year;
  }

  public String director() {
    // TODO  
	  return _director ;
  
  }

  public String title() {
    // TODO 
	  
    return _title;
  }

  public int year() {
    // TODO  
    return _year;
  }

  public boolean equals(Object thatObject) {
    // TODO 
	  if(thatObject==null) return false;
	  if(this ==thatObject) return true;
	  if(!(this.getClass().equals(thatObject.getClass()))){
		  return false;
	  }
	  VideoObj that=(VideoObj) thatObject;
	  return _title.equals(that._title)
			  && _year==that._year
			  && _director.equals(that._director);
   
  }
  private int hcode;
  public int hashCode() {
    // TODO
	  if(hcode==0) {
	hcode=17;
	hcode=37*hcode+_title.hashCode();
	hcode=37*hcode+_year;
	hcode=37*hcode+_director.hashCode();
	}
	return hcode;  
  }

  public int compareTo(Object thatObject) {
    // TODO  
	  VideoObj that=(VideoObj)thatObject;
	   if(_title.compareTo(that._title)>0) {
	 	  return 1;
	   }
	   if(_title.compareTo(that._title)<0) {
	 	  return -1;
	   }
	   if(_director.compareTo(that._director)>0) return 1;
	   if(_director.compareTo(that._director)<0) return -1;
	   if(_year-that._year<0) return -1;
	   if(_year -that._year>0) return 1;
	   return 0;
  }

  public String toString() {
    // TODO  
	  return  _title+ " ("+ _year +") : " +_director;
  }
}
