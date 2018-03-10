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
    this.jsonInit({
	  "message0": "For %1 set %2 to %3",
	  "args0": [
	    {
	      "type": "input_value",
	      "name": "name",
	      "check": "String",
	      "value": "Name"
	    },
	    {
	      "type": "input_value",
	      "name": "property",
	      "check": "String",
		  "value": "Text"
	    },
	    {
	      "type": "input_value",
	      "name": "value",
	      "check": "String",
		  "value": "Hello"
	    }
	  ],
	  "previousStatement": null,
	  "nextStatement": null,
	  "colour": 270,
	  "tooltip": "Set property of widget",
	  "helpUrl": ""
	});
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
	  var value_name = Blockly.JavaScript.valueToCode(block, 'name', Blockly.JavaScript.ORDER_ATOMIC);
	  var value_property = Blockly.JavaScript.valueToCode(block, 'property', Blockly.JavaScript.ORDER_ATOMIC);
	  var value = Blockly.JavaScript.valueToCode(block, 'value');
	  var code = generatePropertySetter(eval(value_name),eval(value_property),value) +';\n';
	  return code;
};

Blockly.JavaScript['test_call'] = function(block) {
  // Print statement into UI console
  var msg = Blockly.JavaScript.valueToCode(block, 'TEXT', Blockly.JavaScript.ORDER_NONE);      
  
  return "fromJava("+msg+ "+'\\n');\n";
};

