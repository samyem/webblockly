// global functions
function getItemNames() {
	var items = gwt_getItemNames();
	console.log(""+items);	
	return items;
}

function getPropertiesForId(id) {
	var props = gwt_getPropertiesForId(id);
	console.log("final props="+props);
	return props;
}

function getEventsForId(id) {
	var events = gwt_getEventsForId(id);
	return events;
}

function itemChange(id){
	console.log("itemChange param: "+id);
	
	var propInput = this.sourceBlock_.inputList[1];
	var propOptions = propInput.fieldRow[1];
	propOptions.getOptions = function(){return gwt_getPropertiesForId(id);}
}

function eventItemChange(id){
	console.log("event itemChange param: "+id);
	
	var propInput = this.sourceBlock_.inputList[0];
	var propOptions = propInput.fieldRow[1];
	propOptions.getOptions = function(){return gwt_getEventsForId(id);}
}

function getInitialProps(){
	var items = getItemNames();
	if(items.length>0){
		return getPropertiesForId(items[0][1]);
	}
}
function getInitialEventss(){
	var items = getItemNames();
	if(items.length>0){
		return getEventsForId(items[0][1]);
	}
}

// blockly defs

Blockly.Blocks['text_console_print'] = {
  init: function() {
    this.jsonInit({
      "message0": 'Print %1 to console',
      "args0": [
        {
          "type": "input_value",
          "name": "TEXT",
        }
      ],
 	  "previousStatement": null,
  	  "nextStatement": null,      
      "colour": Blockly.Blocks.texts.HUE,
      "tooltip": "Print to console",
      "helpUrl": "TODO"
    });
  }
};

Blockly.Blocks['math_parse_number'] = {
  init: function() {
    this.jsonInit({
      "message0": 'Convert %1 to number',
      "args0": [
        {
          "type": "input_value",
          "name": "TEXT",
        }
      ],
      "output": null,
      "colour": 50,
      "tooltip": "Convert string into number",
      "helpUrl": "TODO"
    });
  }
};

Blockly.Blocks['set_property'] = {
		  init: function() {
		    this.appendDummyInput()
		        .appendField("On")
		        .appendField(new Blockly.FieldDropdown(getItemNames, itemChange),"name");

		    this.appendDummyInput()
		    .appendField("set")
	        .appendField(new Blockly.FieldDropdown(getInitialProps),"property");
		    
		    this.appendValueInput("value")
		    .appendField("to")
	        .setCheck("String");
		    
		    this.setPreviousStatement(true, null);
		    this.setNextStatement(true, null);
		    this.setColour(270);
		 this.setTooltip("Set property of widget");
		 this.setHelpUrl("");
		 }
};

Blockly.Blocks['get_property'] = {
		  init: function() {
		    this.appendDummyInput()
		        .appendField("On")
		        .appendField(new Blockly.FieldDropdown(getItemNames, itemChange),"name");

		    this.appendDummyInput()
		    .appendField("get") 
	        .appendField(new Blockly.FieldDropdown(getInitialProps),"property");
		    
		    this.setOutput(true, null);
		    this.setColour(230);
		 this.setTooltip("Get property of widget");
		 this.setHelpUrl("");
		 }
};

Blockly.Blocks['on_event'] = {
		  init: function() {
		    this.appendDummyInput()
		        .appendField("When")
		        .appendField(new Blockly.FieldDropdown(getItemNames, eventItemChange), "name");

		    this.appendDummyInput()
	        .appendField(new Blockly.FieldDropdown(getInitialEventss), "event");
		    
		    this.appendStatementInput("event_handler")
	        .setCheck(null);
		    this.setColour(130);
		 this.setTooltip("Get property of widget");
		 this.setHelpUrl("");
		 }
};

Blockly.Blocks['test_call'] = {
  init: function() {
    this.jsonInit({
      "message0": 'Test %1 call',
      "args0": [
        {
          "type": "input_value",
          "name": "TEXT",
        }
      ],
 	  "previousStatement": null,
  	  "nextStatement": null,      
      "colour": Blockly.Blocks.texts.HUE,
      "tooltip": "Print to console",
      "helpUrl": "TODO"
    });
  }
};

////// Code generators

Blockly.JavaScript['text_console_print'] = function(block) {
  // Print statement into UI console
  var msg = Blockly.JavaScript.valueToCode(block, 'TEXT', Blockly.JavaScript.ORDER_NONE);      
  msg = String(msg);
  return "console.log("+msg+");$('#console').append("+msg+ "+'\\n');\n";
};

Blockly.JavaScript['math_parse_number'] = function(block) {
  // Parse string as numeric value
  var strVal = Blockly.JavaScript.valueToCode(block, 'TEXT', Blockly.JavaScript.ORDER_NONE)  || '0';
  var code = "parseFloat("+strVal+")"; 
  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];
};

Blockly.JavaScript['set_property'] = function(block) {
	  var value_name = block.getFieldValue('name');
	  var value_property = block.getFieldValue('property');
	  var value = Blockly.JavaScript.valueToCode(block, 'value');
	  var code = generatePropertySetter(value_name, value_property, value) +';\n';
	  return code;
};

Blockly.JavaScript['get_property'] = function(block) {
	  var value_name = block.getFieldValue('name');
	  var value_property = block.getFieldValue('property');
	  var code = generatePropertyGetter(value_name, value_property);
	  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];;
};

Blockly.JavaScript['on_event'] = function(block) {
	  var value_name = block.getFieldValue('name');
	  var value_property = block.getFieldValue('event');
	  var statements_handler = Blockly.JavaScript.statementToCode(block, 'event_handler');
	  
	  var code = generateEvent(value_name, value_property, statements_handler);
	  return [code, Blockly.JavaScript.ORDER_FUNCTION_CALL];;
};


Blockly.JavaScript['test_call'] = function(block) {
  // Print statement into UI console
  var msg = Blockly.JavaScript.valueToCode(block, 'TEXT', Blockly.JavaScript.ORDER_NONE);      
  
  return "fromJava("+msg+ "+'\\n');\n";
};

