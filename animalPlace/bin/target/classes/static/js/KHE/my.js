const myServiceM = {
	selectAll: function(pagenum, callback) {
		$.getJSON(
			`/my/contentsMB?pagenum=${pagenum}`,
			function(data) {
				//data : 응답되는 JSON 형태의 객체({replyCnt:댓글개수, list:[...]})
				callback(data.list,data.pageMaker);
			}
		)
	},
	selectprv: function(callback) {
		$.getJSON(
			`/my/contentsMBprv`,
			function(data) {
				callback(data);
			}
		)
	}
}
const myServiceP = {
	selectprv : function(callback){
		$.getJSON(
			`/my/contentsPBprv`,
			function(data){
				callback(data);
			}
		)
	},
	selectAll: function(pagenum, callback){
		$.getJSON(
			`/my/contentsPB?pagenum=${pagenum}`,
			function(data){
				callback(data.list,data.pageMaker);
			}
		)
	}
}
const myServiceN = {
	select : function(pagenum, callback){
		$.getJSON(
			`/my/noteList?pagenum=${pagenum}`,
			function(data){
				callback(data.list,data.pageMaker);
			}
		)
	}
}