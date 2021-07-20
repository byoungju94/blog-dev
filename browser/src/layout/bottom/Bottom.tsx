import React from "react";

function Bottom() {
	return (
		<footer style={styleSheet.wrapper} className="footer mt-auto py-3 bg-light">
			<div className="container">
				<span className="text-muted">Place sticky footer content here.</span>
			</div>
		</footer>
	)
}

const styleSheet = {
	wrapper: {
		flexShrink: 0,
		textAlign: 'center',
		backgroundColor: 'tomato',
		color: 'white'
	} as React.CSSProperties
};

export default Bottom;