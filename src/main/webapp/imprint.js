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


RelationImprint = function( cls, tgtCls, tgtId, tgt, attrs)
{
	 this.cls=cls;
	 this.tgtCls=tgtCls;
	 this.attrs = attrs;
	 this.targetId=tgtId;
	 this.target = tgt;
}

RelationImprint.prototype =
{
		getAttributes: function() { return this.attrs; },
		getClass: function() { return this.cls; },
		getTargetObjectClass: function() { return this.tgtCls; },
		getTargetId: function() { return this.targetId; },
		getTargetObject: function() { return this.target; }
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

ObjectValueImprint = function( tgtCls, tgtId, tgtObj, attrs )
{
 this.tgtId=tgtId;
 this.tgtCls=tgtCls;
 this.tgtObj=tgtObj;
 this.attrs = attrs;
}

ObjectValueImprint.prototype = 
{
		getTargetId: function() { return this.tgtId; },
		getTargetObjectClass: function() { return this.tgtCls; },
		getTargetObject: function() { return this.tgtObj; },
		
		getAttributes: function() { return this.attrs; },

}

IdImprint = function( clustId, modId, objId )
{
	this.clusterId = clustId;
	this.moduleId = modId;
	this.objectId = objId;
}

IdImprint.prototype = 
{
 getClusterId: function() { return this.clusterId ; },
 getModuleId: function() { return this.moduleId ; },
 getObjectId: function(){ return this.objectId ; },

 toString: function() { return this.clusterId+":"+this.moduleId+":"+this.objectId; }
}

DataBlock = function( data, classes, total )
{
	this.data = data;
	this.classes = classes;
	this.total=total;
}

DataBlock.prototype = 
{
 getData: function() { return this.data; },
 getClasses: function() { return this.classes; },
 getTotal: function(){ return this.total; } 
}