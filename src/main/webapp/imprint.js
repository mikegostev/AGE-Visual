ObjectImprint = function(id, cls, attrs, rels) 
{
	this.id=id;
	this.cls=cls;
	this.attrs=attrs;
	this.rels=rels;
}

ObjectImprint.prototype = 
{
		getId: function() { return this.id.objectId; },
		
		getFullId: function() { return this.id; },
		
		getClass: function() { return this.cls; },
		
		getAttributes: function() { return this.attrs; },

		getRelations: function() { return this.rels; }
}

ClassImprint = function(name,type,custom)
{ 
 this.name = name;
 this.type = type;
 this.custom=custom;
}

ClassImprint.prototype = 
{

 getName: function() { return this.name; },
 getType: function() { return this.type; },
 isCustom: function() { return this.custom; }

}

AttributeImprint = function( cls, values )
{
 this.cls=cls;
 this.values = values;
}

AttributeImprint.prototype = 
{
		getClass: function() { return this.cls; },
		
		getValues: function() { return this.values; },
		
		getValue: function() { if( this.values == null ) return null; return this.values[0]; },

		getValueCount: function() { return this.values.length;}
}


RelationImprint = function( cls, tgtId, tgt, attrs)
{
	 this.cls=cls;
	 this.attrs = attrs;
	 this.targetId=tgtId;
	 this.target = tgt;
}

RelationImprint.prototype =
{
		getAttributes: function() { return this.attrs; },
		getClass: function() { return this.cls; },
		getTargetId: function() { return this.targetId; },
		getTarget: function() { return this.target; }
}

ValueImprint = function( val, attrs )
{
 this.val=val;
 this.attrs = attrs;
}

ValueImprint.prototype = 
{
		get: function() { return this.val; },
		
		getAttributes: function() { return this.attrs; },

}

IdImprint = function( clustId, modId, objId )
{
	this.clusterId = clustId;
	this.moduleId = modId;
	this.objectId = objId;
}