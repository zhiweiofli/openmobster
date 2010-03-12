/**
 * Copyright (c) {2003,2009} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package test.openmobster.device.agent.api;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import org.openmobster.core.common.Utilities;
import org.openmobster.core.common.database.HibernateManager;

/**
 * @author openmobster@gmail.com
 */
public class TicketDS 
{
	private static Logger log = Logger.getLogger(TicketDS.class);
	
	private HibernateManager hibernateManager;
	
	public TicketDS()
	{
		
	}

	public HibernateManager getHibernateManager() 
	{
		return hibernateManager;
	}

	public void setHibernateManager(HibernateManager hibernateManager) 
	{
		this.hibernateManager = hibernateManager;
	}
	//-------------------------------------------------------------------------------------------------------
	public String create(Ticket ticket) throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			String ticketId = "ticket://"+Utilities.generateUID();
			ticket.setTicketId(ticketId);
			session.save(ticket);
						
			tx.commit();
			
			return ticket.getTicketId();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			
			throw new ModelException(e);
		}
	}
	
	public List<Ticket> readAll() throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			List<Ticket> tickets = new ArrayList<Ticket>();
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			String query = "from Ticket";
			
			List cour = session.createQuery(query).list();
			
			if(cour != null)
			{
				tickets.addAll(cour);
				for(Object ticket: tickets)
				{
					Hibernate.initialize(((Ticket)ticket).getTechnician());
					Hibernate.initialize(((Ticket)ticket).getEquipment());
					Hibernate.initialize(((Ticket)ticket).getCustomerInfo());
				}
			}
						
			tx.commit();
			
			return tickets;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ModelException(e);
		}
	}
	
	public Ticket readByTicketId(String ticketId) throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			Ticket ticket = null;
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			String query = "from Ticket where ticketId=?";
			
			ticket = (Ticket)session.createQuery(query).setParameter(0, ticketId).uniqueResult();
			
			if(ticket != null)
			{
				Hibernate.initialize(((Ticket)ticket).getTechnician());
				Hibernate.initialize(((Ticket)ticket).getEquipment());
				Hibernate.initialize(((Ticket)ticket).getCustomerInfo());
			}
						
			tx.commit();
			
			return ticket;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ModelException(e);
		}
	}
	
	public void update(Ticket ticket) throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
						
			session.update(ticket);
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ModelException(e);
		}
	}
	
	public void delete(Ticket ticket) throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			session.delete(ticket);
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ModelException(e);
		}
	}
	
	public void deleteAll() throws ModelException
	{
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			tx = session.beginTransaction();
			
			String query = "from Ticket";
			List cour = session.createQuery(query).list();			
			if(cour != null)
			{
				for(Object ticket: cour)
				{
					session.delete(ticket);
				}
			}						
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ModelException(e);
		}
	}
}
