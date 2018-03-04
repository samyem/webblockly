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

////// Code generators

Blockly.JavaScript['text_console_print'] = function(block) {
  // Print statement into UI console
  var msg = Blockly.JavaScript.valueToCode(block, 'TEXT', Blockly.JavaScript.ORDER_NONE);      
  
  return "console.log("+msg+");$('#console').append("+msg+ "+'\\n');\n";
};
