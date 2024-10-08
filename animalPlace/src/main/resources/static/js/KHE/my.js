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