package com.heroku.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * TODO: Javadoc
 *
 * @author Naaman Newbold
 */
public class Release implements Serializable {

    private static final long serialVersionUID = 1L;

    String name, descr,user, commit;
    String created_at;
    Map<String, String> env;
    List<String> addons;
    Map<String, Object> pstable;

		public String toString() { 
			return name + " " + descr + " " + user + " " + commit + " " + created_at;
		}

		public boolean equals(Object o) {
			if (o instanceof Release) {
				Release r = (Release) o;
				return 	this.name.equals(r.name) && 
								this.user.equals(r.user) && 
								this.descr.equals(r.descr) && 
								this.created_at.equals(r.created_at);
			}
			return false;
		}

		private int hashOrOne(String field) {
			if (field == null) {
				return 1; 
			} else {
				return field.hashCode();
			}
		}

		public int hashCode() {
				return 	31 * hashOrOne(name) *
								hashOrOne(user) * 
								hashOrOne(descr) * 
							  hashOrOne(created_at);
			
		}

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return descr;
    }

    private void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }

    public String getCommit() {
        return commit;
    }

    private void setCommit(String commit) {
        this.commit = commit;
    }

    public String getCreatedAt() {
        return created_at;
    }

    private void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Map<String, String> getEnv() {
        return env;
    }

    private void setEnv(Map<String, String> env) {
        this.env = env;
    }

    public List<String> getAddons() {
        return addons;
    }

    private void setAddons(List<String> addons) {
        this.addons = addons;
    }

    public Map<String, Object> getPSTable() {
        return pstable;
    }

    private void setPstable(Map<String, Object> pstable) {
        this.pstable = pstable;
    }
}
