import { useEffect, useState } from "react";
import { Button, Table } from "react-bootstrap";

export const ReportView = ({ headers, data, title, asTable = true }) => {
    const [visibleColumns, setVisibleColumns] = useState([]);

    useEffect(() => {
        setVisibleColumns(headers);
        
    }, [headers]);

    const toggleColumn = (column) => {
        if (visibleColumns.includes(column)) {
            setVisibleColumns(visibleColumns.filter(col => col !== column));
        } else {
            setVisibleColumns([...visibleColumns, column]);
        }
    };

    const showTable = () => {
        return (
            <Table striped bordered hover responsive="lg">
                <thead>
                    <tr>
                        {visibleColumns.map(header =>
                            <th key={header} className="p-3 mb-2 bg-dark text-white">
                                {header}
                                <span
                                    style={{ marginLeft: '5px', cursor: 'pointer' }}
                                    onClick={() => toggleColumn(header)}
                                >
                                    {visibleColumns.includes(header) ? '[-]' : '[+]'}
                                </span>
                            </th>
                        )}
                    </tr>
                </thead>
                <tbody>
                    {data.map((rows, index) => {
                       
                        return (                            
                            <tr key={index}>
                                {visibleColumns.map((column, index) => (
                                    <td key={index}>{rows[column]}</td>
                                ))}
                            </tr>
                        );
                    })}
                </tbody>
            </Table>
        );
    };

    const showGraphic = () => {
        return (
            <div>
                <h1>Graphic</h1>
            </div>
        );
    };

    const defaultConfiguration = () => {
        setVisibleColumns(headers);
    }

    return (
        <>
            <h1 className="text-center mt-5" style={{ color: 'black' }}>{title}</h1>
            <div className="d-flex justify-content-center mt-3 mb-5">
                <Button variant="outline-dark" onClick={() => defaultConfiguration()}>Default</Button>
            </div>            
            {asTable ? showTable() : showGraphic()}
        </>
    );

};