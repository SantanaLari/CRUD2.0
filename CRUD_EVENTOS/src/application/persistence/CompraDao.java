package application.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.model.Caravana;
import application.model.Compra;
import application.model.Evento;
import application.model.Ingresso;

public class CompraDao implements ICompraDao {

	private Connection c;
	
	public CompraDao() throws ClassNotFoundException, SQLException{
		GenericDao gDao = new GenericDao();
		c = gDao.getConnection();
	}
	
	@Override
	public void insereCompra(Compra cp) throws SQLException {
		String sql = "INSERT INTO compra(codigo, nome, email, codigoCaravana) VALUES (?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
	
		ps.setInt(1, cp.getCodigo());
		ps.setString(2, cp.getNome());
		ps.setString(3, cp.getEmail());
		ps.setInt(4, cp.getCaravana().getCodigo());
		ps.execute();
		ps.close();
		
	}
	
	@Override
	public void atualizaCompra(Compra cp) throws SQLException {
		String sql = "UPDATE compra SET nome = ?, email = ? WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNome());
		ps.setString(2, cp.getEmail());
		ps.setInt(3, cp.getCodigo());
		ps.execute();
		ps.close();
	}
	
	@Override
	public void excluiCompra(Compra cp) throws SQLException {
		String sql = "DELETE FROM compra WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getCodigo());
		ps.execute();
		ps.close();
	}
	
	//Botão visualizar compra
	@Override
	public List<Compra> listagemFiltrada() throws SQLException {
		List<Compra> listaFiltrada = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cp.nome, cv.nome ");
		sql.append("FROM compra cp, caravana cv ");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		/*	Evento e = new Evento();
			e.setNome(rs.getString("evento"));
			*/
			Caravana cv = new Caravana();
		//cv.setNome(rs.getString("nome"));
			
			Compra cp = new Compra();
			cp.setNome(rs.getString("nome"));
			cp.setCaravana(cv);
			
			listaFiltrada.add(cp);
		}
		
		rs.close();
		ps.close();
		
		return listaFiltrada;
	}
	
	//botão listar/salvar
	@Override
	public List<Compra> buscaCompras() throws SQLException {
		List<Compra> listaCompra = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
/*		sql.append("SELECT cv.nome, cv.preco, ");
		sql.append("cp.nome, cp.email ");
		sql.append("FROM caravana cv, compra cp ");
		sql.append("WHERE cp.idCaravana = cv.id ");*/
		sql.append("SELECT cp.codigo, cp.nome, cp.email, cv.codigo ");
		sql.append("FROM compra cp, caravana cv ");
		
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
		/*	Evento e = new Evento();
	    	e.setNome(rs.getString("nome"));
	    	e.setData(rs.getDate("dia"));
	    	e.setHora(rs.getInt("hora"));
	    	e.setRua(rs.getString("rua"));
	    	e.setNumero(rs.getInt("numero"));
	    	
	    	Ingresso i = new Ingresso();
	    	i.setPreco(rs.getDouble("preco"));
	    	i.setTipo(rs.getString("tipo"));
	    	*/
	    	Caravana cv = new Caravana();
	    	cv.setCodigo(rs.getInt("codigo"));
	    	cv.setNome(rs.getString("nome"));
	    //	cv.setCapacidade(rs.getInt("capacidade"));
	    //	cv.setPreco(rs.getDouble("preco"));
	    //	cv.setUf(rs.getString("uf"));
	    	
	    	Compra cp = new Compra();
	    	cp.setCodigo(rs.getInt("codigo"));
	    	cp.setNome(rs.getString("nome"));
	    	cp.setEmail(rs.getString("email"));
	    	cp.setCaravana(cv);
	    	
	    	listaCompra.add(cp);
		}
		
		rs.close();
		ps.close();
		
		return listaCompra;
	}
	
	//botão de opções
	public List<Compra> visualizaOpcao() throws SQLException {
		List<Compra> listaVisualizacao = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
	/*	sql.append("SELECT ev.codigo AS codigo_evento, ");
		sql.append("ev.nome, ev.dia, ");
		sql.append("ig.codigo, ig.preco AS preco_ingresso, ");
		sql.append("cv.codigo AS id_caravana, cv.preco AS preco_caravana");
		sql.append("CASE WHEN (ev.UF = cv.UF) ");
		sql.append("THEN ");
		sql.append("SUM(ig.preco + cv.preco)" );
		sql.append("ELSE ");
		sql.append("ig.preco ");
	    sql.append("END AS total ");
	    sql.append("FROM evento ev INNER JOIN ingresso ig ");
	    sql.append("ON ev.codigo = ig.codigoEvento ");
	    sql.append("LEFT OUTER JOIN caravana cv ");
	    sql.append("ON cv.uf = ev.uf ");
	    sql.append("GROUP BY ev.codigo, ev.nome, ev.dia, ig.codigo, ig.preco, cv.codigo, "
	    		+ "cv.preco, cv.uf, ev.uf ");
	    sql.append("ORDER BY total ASC");*/
		
		sql.append("SELECT cv.codigo, cv.preco ");
		sql.append("FROM caravana cv ");
	    PreparedStatement ps = c.prepareStatement(sql.toString());
	    ResultSet rs = ps.executeQuery();
	    while(rs.next()) {
	    //	Evento e = new Evento();
	    //	e.setCodigo(rs.getInt("codigo"));
	    //	e.setNome(rs.getString("nome"));
	    //	e.setData(rs.getString("data"));
	    	
	  /*   	Ingresso i = new Ingresso();
	    	i.setCodigo(rs.getInt("codigoIngresso"));
	    	i.setPreco(rs.getDouble("precoIngresso"));
	    */
	    	Caravana cv = new Caravana();
	    	cv.setCodigo(rs.getInt("codigo"));
	    	cv.setPreco(rs.getDouble("preco"));
	    	
	    	Compra cp = new Compra();
	    	cp.setCaravana(cv);
	    //	cp.setEvento(e);
	    	
	    	listaVisualizacao.add(cp);
	    }
	    
	    rs.close();
	    ps.close();
	    
		return listaVisualizacao;

	}
/*	CODIGO ORIGINAL
	@Override
	public void insereCompra(Compra cp) throws SQLException {
		String sql = "INSERT INTO compra VALUES (?,?,?,?,?,?)";
		PreparedStatement ps = c.prepareStatement(sql);
	
		ps.setInt(1, cp.getCodigo());
		ps.setString(2, cp.getNome());
		ps.setString(3, cp.getEmail());
		ps.setInt(4, cp.getEvento().getCodigo());
		ps.setInt(5, cp.getIngresso().getCodigo());
		ps.setInt(6, cp.getCaravana().getCodigo());
		ps.execute();
		ps.close();
	}

	@Override
	public void atualizaCompra(Compra cp) throws SQLException {
		String sql = "UPDATE compra SET nomeComprador = ?, email = ?, "
				+ "codigoEvento = ?, codigoIngresso = ?, "
				+ "codigoCaravana = ? WHERE codigoCompra = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cp.getNome());
		ps.setString(2, cp.getEmail());
		ps.setInt(3, cp.getEvento().getCodigo());
		ps.setInt(4, cp.getIngresso().getCodigo());
		ps.setInt(5, cp.getCaravana().getCodigo());
		ps.setInt(6, cp.getCodigo());
		ps.execute();
		ps.close();
	}

	@Override
	public void excluiCompra(Compra cp) throws SQLException {
		String sql = "DELETE FROM compra WHERE codigo = ?";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cp.getCodigo());
		ps.execute();
		ps.close();
	}

	@Override
	public List<Compra> listagemFiltrada() throws SQLException {
		List<Compra> listaFiltrada = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT cp.nome, ev.nome, cv.nome ");
		sql.append("SUM(ig.preco + cv.preco) AS total_compra ");
		sql.append("FROM compra cp, ingresso ig, evento ev, caravana cv ");
		sql.append("WHERE cp.codigoIngresso = ig.codigo ");
		sql.append("AND cp.codigoEnveot = ev.codigo ");
		sql.append("AND cp.codigoCaravana = cv.codigo ");
		sql.append("GROUP BY cp.nome, ev.nome, cv.nome ");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Evento e = new Evento();
			e.setNome(rs.getString("nomeEvento"));
			
			Caravana cv = new Caravana();
			cv.setNome(rs.getString("nomeCaravana"));
			
			Compra cp = new Compra();
			cp.setNome(rs.getString("nomeComprador"));
			
			listaFiltrada.add(cp);
		}
		
		rs.close();
		ps.close();
		
		return listaFiltrada;
	}
	
	@Override
	public List<Compra> buscaCompras() throws SQLException {
		List<Compra> listaCompra = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ev.nome, ev.dia, ev.hora, ev.rua, ev.numero, ");
		sql.append("ig.tipo, ig.preco, ");
		sql.append("cv.nome, cv.preco, ");
		sql.append("cp.nome, cp.email ");
		sql.append("FROM evento ev, ingresso ig, caravana cv, compra cp ");
		sql.append("WHERE cp.idEvento = ev.id ");
		sql.append("AND cp.idIngresso = ig.id ");
		sql.append("AND cp.idCaravana = cv.id ");
		PreparedStatement ps = c.prepareStatement(sql.toString());
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Evento e = new Evento();
	    	e.setNome(rs.getString("nome"));
	    	e.setData(rs.getDate("dia"));
	    	e.setHora(rs.getInt("hora"));
	    	e.setRua(rs.getString("rua"));
	    	e.setNumero(rs.getInt("numero"));
	    	
	    	Ingresso i = new Ingresso();
	    	i.setPreco(rs.getDouble("preco"));
	    	i.setTipo(rs.getString("tipo"));
	    	
	    	Caravana cv = new Caravana();
	    	cv.setNome(rs.getString("nome"));
	    	cv.setPreco(rs.getDouble("preco"));
	    	
	    	Compra cp = new Compra();
	    	cp.setNome(rs.getString("Nome"));
	    	cp.setEmail(rs.getString("Email"));
	    	
	    	listaCompra.add(cp);
		}
		
		rs.close();
		ps.close();
		
		return listaCompra;
	}

	@Override
	public List<Compra> visualizaOpcao() throws SQLException {
		List<Compra> listaVisualizacao = new ArrayList<Compra>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ev.codigo AS codigo_evento, ");
		sql.append("ev.nome, ev.dia, ");
		sql.append("ig.codigo, ig.preco AS preco_ingresso, ");
		sql.append("cv.codigo AS id_caravana, cv.preco AS preco_caravana");
		sql.append("CASE WHEN (ev.UF = cv.UF) ");
		sql.append("THEN ");
		sql.append("SUM(ig.preco + cv.preco)" );
		sql.append("ELSE ");
		sql.append("ig.preco ");
	    sql.append("END AS total ");
	    sql.append("FROM evento ev INNER JOIN ingresso ig ");
	    sql.append("ON ev.codigo = ig.codigoEvento ");
	    sql.append("LEFT OUTER JOIN caravana cv ");
	    sql.append("ON cv.uf = ev.uf ");
	    sql.append("GROUP BY ev.codigo, ev.nome, ev.dia, ig.codigo, ig.preco, cv.codigo, "
	    		+ "cv.preco, cv.uf, ev.uf ");
	    sql.append("ORDER BY total ASC");
	    PreparedStatement ps = c.prepareStatement(sql.toString());
	    ResultSet rs = ps.executeQuery();
	    while(rs.next()) {
	    	Evento e = new Evento();
	    	e.setCodigo(rs.getInt("codigoEvento"));
	    	e.setNome(rs.getString("nomeEvento"));
	    	e.setData(rs.getDate("dataEvento"));
	    	
	    	Ingresso i = new Ingresso();
	    	i.setCodigo(rs.getInt("codigoIngresso"));
	    	i.setPreco(rs.getDouble("precoIngresso"));
	    
	    	Caravana cv = new Caravana();
	    	cv.setCodigo(rs.getInt("codigoCaravana"));
	    	cv.setPreco(rs.getDouble("precoCaravana"));
	    	
	    	Compra cp = new Compra();
	    	
	    	listaVisualizacao.add(cp);
	    }
	    
	    rs.close();
	    ps.close();
	    
		return listaVisualizacao;
	}*/
}
