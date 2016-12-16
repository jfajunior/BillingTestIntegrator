package br.com.gvt.integrator.ui;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;




/**
 * PSTabbedPaneUI is an approximation of the design style of the PhotoShop (PS) tabs.
 * 
 * @author Jon Lipsky 2005 
 * @author José Júnior
 *         GVT - 2013.07
 */
public class PSTabbedPaneUI extends BasicTabbedPaneUI {
	
	private static final Insets NO_INSETS = new Insets(2, 0, 0, 0);
	
	/** The font to use for the selected tab */
	private Font _fontBold;
	
	/** The font metrics for the selected font */
	private FontMetrics _boldFontMetrics;
	
	/** The color to use to fill in the background */
	private Color _fillColor;
	
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	// Custom installation methods.
	// ----------------------------------------------------------------------------------------------------------------
	public static ComponentUI createUI(JComponent c) {
		return new PSTabbedPaneUI();
	}
	
	
	
	
	@Override
	protected void installDefaults() {
		super.installDefaults();
		tabAreaInsets.left = 4;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		tabInsets = selectedTabPadInsets;
		
		_fillColor = tabPane.getBackground().darker();
		_fontBold = tabPane.getFont().deriveFont(Font.BOLD);
		_boldFontMetrics = tabPane.getFontMetrics(_fontBold);
	}
	
	
	
	
	// ----------------------------------------------------------------------------------------------------------------
	// Custom sizing methods.
	// ----------------------------------------------------------------------------------------------------------------
	@Override
	public int getTabRunCount(JTabbedPane pane) {
		return 1;
	}
	
	
	
	
	@Override
	protected Insets getContentBorderInsets(int tabPlacement) {
		return NO_INSETS;
	}
	
	
	
	
	@Override
	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
		int vHeight = fontHeight;
		
		if (vHeight % 2 > 0) {
			vHeight += 4;
		}
		return vHeight;
	}
	
	
	
	
	@Override
	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
		
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + metrics.getHeight() + 20;
		
	}
	
	
	
	
	// ----------------------------------------------------------------------------------------------------------------
	// Custom painting methods.
	// ----------------------------------------------------------------------------------------------------------------
	
	
	// ----------------------------------------------------------------------------------------------------------------
	// Methods that we want to suppress the behavior of the superclass.
	// ----------------------------------------------------------------------------------------------------------------
	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		
		Polygon shape = new Polygon();
		
		// Adicionado mais um ponto na altura para cobrir o pixel que aparece a mais nas linhas de fundo.
		shape.addPoint(x, y + h + 1);
		shape.addPoint(x, y);
		shape.addPoint(x + w - (h / 2), y);
		
		if (isSelected) {
			g.setColor(tabPane.getBackground());
			shape.addPoint(x + w + (h / 2), y + h + 1);
		}
		else if (tabIndex == (rects.length - 1)) {
			g.setColor(shadow.darker());
			shape.addPoint(x + w + (h / 2), y + h + 1);
		}
		else {
			g.setColor(shadow.darker());
			shape.addPoint(x + w, y + (h / 2));
			shape.addPoint(x + w, y + h + 1);
		}
		
		g.fillPolygon(shape);
	}
	
	
	
	
	@Override
	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
			boolean isSelected) {
		
		g.setColor(Color.BLACK);
		// Primeira reta vertical.
		g.drawLine(x, y, x, y + h);
		// Reta orizontal.
		g.drawLine(x, y, x + w - (h / 2), y);
		// Reta oblíqua.
		g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);
		
		if (isSelected) {
			g.drawLine(x + 1, y + 1, x + 1, y + h);
			g.drawLine(x + 1, y + 1, x + w - (h / 2), y + 1);
			g.drawLine(x + w - (h / 2), y, x + w + (h / 2), y + h);
			g.drawLine(x + w - (h / 2), y + 1, x + w + (h / 2) - 1, y + h);
			g.drawLine(x + w - (h / 2), y + 2, x + w + (h / 2) - 2, y + h);
		}
	}
	
	
	
	
	@Override
	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
			int h) {
		
		Rectangle selectedRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		
		selectedRect.width = selectedRect.width + (selectedRect.height / 2) - 1;
		
		g.setColor(Color.BLACK);
		g.drawLine(x, y, selectedRect.x, y);
		g.drawLine(selectedRect.x - 2 + selectedRect.width + 1, y, x + w, y);
		
		g.drawLine(x - 1, y + 1, selectedRect.x, y + 1);
		g.drawLine(selectedRect.x + 1, y + 1, selectedRect.x + 1, y);
		g.drawLine(selectedRect.x - 2 + selectedRect.width + 2, y + 1, x + w, y + 1);
	}
	
	
	
	
	@Override
	protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}
	
	
	
	
	@Override
	protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}
	
	
	
	
	@Override
	protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,
			int selectedIndex, int x, int y, int w, int h) {
		// Do nothing
	}
	
	
	
	
	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
			Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		// Do nothing
	}
	
	
	
	
	@Override
	protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
		
		g.setColor(_fillColor);
		g.fillRect(0, 0, tabPane.getBounds().width, rects[0].height + 3);
		
		super.paintTabArea(g, tabPlacement, selectedIndex);
	}
	
	
	
	
	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
			Rectangle textRect, boolean isSelected) {
		
		// Dois pontos adicionados na altura para melhor efeito na seleção.
		if (isSelected) {
			int vDifference = (int) (_boldFontMetrics.getStringBounds(title, g).getWidth()) - textRect.width;
			
			textRect.x -= (vDifference / 2);
			textRect.y += 2;
			super.paintText(g, tabPlacement, _fontBold, _boldFontMetrics, tabIndex, title, textRect, isSelected);
		}
		else {
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
		}
	}
	
	
	
	
	@Override
	protected int getTabLabelShiftY(int tabPlacement, int tabIndex,
			boolean isSelected) {
		return 0;
	}
}
