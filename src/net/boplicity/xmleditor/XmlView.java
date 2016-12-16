/*
 * Copyright 2006-2008 Kees de Kooter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.boplicity.xmleditor;


import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;
import javax.swing.text.PlainView;
import javax.swing.text.Segment;
import javax.swing.text.Utilities;




/**
 * IMPORTANT NOTE: Regex should contain 1 group.
 * Using PlainView here because we don't want line wrapping to occur.
 * 
 * @author kees
 * @author José Júnior
 * 
 */
public class XmlView extends PlainView {
	
	private static HashMap<Pattern, Color> patternColors;
	private static String GENERIC_XML_NAME = "[A-Za-z]+[A-Za-z0-9\\-_]*(:[A-Za-z]+[A-Za-z0-9\\-_]+)?";
	private static String TAG_PATTERN = "</?(" + GENERIC_XML_NAME + ")";
	private static String TAG_START_PATTERN = "(</|<)";
	private static String TAG_END_PATTERN = "(>|/>)";
	private static String TAG_ATTRIBUTE_PATTERN = "(" + GENERIC_XML_NAME + ")\\w*\\=";
	private static String TAG_ATTRIBUTE_VALUE = "\\w*\\=\\w*(\"[^\"]*\")";
	private static String TAG_COMMENT = "(<\\!--[\\w ]*-->)";
	private static String TAG_CDATA = "(<\\!\\[CDATA\\[.*\\]\\]>)";
	
	private static final Color COLOR_OCEAN_GREEN = new Color(63, 127, 127);
	private static final Color COLOR_WEB_BLUE = new Color(0, 166, 255);
	private static final Color COLOR_PINK = new Color(127, 0, 127);
	
	static {
		// NOTE: the order is important!
		patternColors = new LinkedHashMap<Pattern, Color>();
		patternColors.put(Pattern.compile(TAG_PATTERN), Color.BLUE); // COLOR_OCEAN_GREEN | Color.BLUE
		patternColors.put(Pattern.compile(TAG_CDATA), COLOR_WEB_BLUE);
		patternColors.put(Pattern.compile(TAG_ATTRIBUTE_PATTERN), COLOR_PINK);
		patternColors.put(Pattern.compile(TAG_START_PATTERN), COLOR_PINK); // COLOR_OCEAN_GREEN
		patternColors.put(Pattern.compile(TAG_END_PATTERN), COLOR_PINK); // COLOR_OCEAN_GREEN
		patternColors.put(Pattern.compile(TAG_COMMENT), Color.GRAY);
		patternColors.put(Pattern.compile(TAG_ATTRIBUTE_VALUE), COLOR_OCEAN_GREEN); // Color.BLUE | COLOR_OCEAN_GREEN
	}
	
	
	
	
	public XmlView(Element element) {
		super(element);
		
		// Set tabsize to 4 (instead of the default 8).
		getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
	}
	
	
	
	
	@Override
	protected int drawUnselectedText(Graphics graphics, int x, int y, int p0, int p1) throws BadLocationException {
		
		// Check for no changes.
		if (p1 - p0 == 0) {
			return 0;
		}
		
		Document doc = getDocument();
		String text = doc.getText(p0, p1 - p0);
		
		SortedMap<Integer, Integer> startMap = new TreeMap<Integer, Integer>();
		SortedMap<Integer, Color> colorMap = new TreeMap<Integer, Color>();
		
		// Match all regexes on this snippet, store positions.
		Matcher matcher;
		for (Map.Entry<Pattern, Color> entry : patternColors.entrySet()) {
			
			matcher = entry.getKey().matcher(text);
			
			while (matcher.find()) {
				startMap.put(matcher.start(1), matcher.end());
				colorMap.put(matcher.start(1), entry.getValue());
			}
		}
		
		int position = 0;
		Segment segment = getLineBuffer();
		
		// Colour the parts.
		for (Map.Entry<Integer, Integer> entry : startMap.entrySet()) {
			int start = entry.getKey();
			int end = entry.getValue();
			
			if (position < start) {
				// Warning! Changing font causes cursor misposition when editing!!
				// graphics.setFont(graphics.getFont().deriveFont(Font.BOLD));
				graphics.setColor(Color.BLACK);
				doc.getText(p0 + position, start - position, segment);
				x = Utilities.drawTabbedText(segment, x, y, graphics, this, position);
			}
			
			// graphics.setFont(graphics.getFont().deriveFont(Font.PLAIN));
			graphics.setColor(colorMap.get(start));
			position = end;
			doc.getText(p0 + start, position - start, segment);
			x = Utilities.drawTabbedText(segment, x, y, graphics, this, start);
		}
		
		// Paint possible remaining text black.
		if (position < text.length()) {
			// graphics.setFont(graphics.getFont().deriveFont(Font.BOLD));
			graphics.setColor(Color.BLACK);
			doc.getText(p0 + position, text.length() - position, segment);
			x = Utilities.drawTabbedText(segment, x, y, graphics, this, position);
		}
		
		return x;
	}
	
}
