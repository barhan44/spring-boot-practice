import React, {Component} from 'react';
import {Table} from 'antd';

import './App.css';
import {getAllStudents} from './client';
import Container from "./Container";

class App extends Component {

    state = {
        students: []
    };

    componentDidMount() {
        this.fetchStudents();
    }

    fetchStudents = () => {
        getAllStudents().then(res => {
            res.json().then(students => {
                this.setState({students});
            });
        });
    };

    render() {

        const {students} = this.state;

        if (students && students.length) {
            const columns = [
                {
                    title: 'Student ID',
                    dataIndex: 'studentId',
                    key: 'studentId'
                },
                {
                    title: 'First Name',
                    dataIndex: 'firstName',
                    key: 'firstName'
                },
                {
                    title: 'Last Name',
                    dataIndex: 'lastName',
                    key: 'lastName'
                },
                {
                    title: 'Email',
                    dataIndex: 'email',
                    key: 'email'
                },
                {
                    title: 'Gender',
                    dataIndex: 'gender',
                    key: 'gender'
                },
            ];

            return (
                <Container>
                    <Table
                        dataSource={students}
                        columns={columns}
                        rowKey='studentId'
                        pagination={false}
                    />
                </Container>
            );
        }
        return <h1>No Students found...</h1>
    };
}

export default App;
