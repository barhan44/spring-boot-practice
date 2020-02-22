import React, { Component } from "react";
import { Avatar, Icon, Spin, Table, Modal } from "antd";

import "./App.css";
import { getAllStudents } from "./client";
import Container from "./Container";
import Footer from "./Footer";
import AddStudentForm from "./forms/AddStudentForm";
import { errorNotification } from "./Notification";

const getIndicatorIcon = () => <Icon type="loading" style={{ fontSize: 24 }} />;

class App extends Component {
  state = {
    students: [],
    isFetching: false,
    isAddStudentModalVisible: false
  };

  componentDidMount() {
    this.fetchStudents();
  }

  openAddStudentModal = () => this.setState({ isAddStudentModalVisible: true });

  closeAddStudentModal = () =>
    this.setState({ isAddStudentModalVisible: false });

  fetchStudents = () => {
    this.setState({
      isFetching: true
    });
    getAllStudents()
      .then(res => {
        res.json().then(students => {
          this.setState({
            students,
            isFetching: false
          });
        });
      })
      .catch(error => {
        const message = error.error.message;
        const description = error.error.error;
        errorNotification(message, description);
        this.setState({
          isFetching: false
        });
      });
  };

  render() {
    const { students, isFetching, isAddStudentModalVisible } = this.state;

    if (isFetching) {
      return (
        <Container>
          <Spin indicator={getIndicatorIcon()} />
        </Container>
      );
    }

    if (students && students.length) {
      const columns = [
        {
          title: "",
          key: "avatar",
          render: (text, student) => (
            <Avatar size="large">
              {`${student.firstName.charAt(0).toUpperCase()}
                            ${student.lastName.charAt(0).toUpperCase()}`}
            </Avatar>
          )
        },
        {
          title: "Student ID",
          dataIndex: "studentId",
          key: "studentId"
        },
        {
          title: "First Name",
          dataIndex: "firstName",
          key: "firstName"
        },
        {
          title: "Last Name",
          dataIndex: "lastName",
          key: "lastName"
        },
        {
          title: "Email",
          dataIndex: "email",
          key: "email"
        },
        {
          title: "Gender",
          dataIndex: "gender",
          key: "gender"
        }
      ];

      return (
        <Container>
          <Table
            style={{ marginBottom: "100px" }}
            dataSource={students}
            columns={columns}
            rowKey="studentId"
            pagination={false}
          />
          <Modal
            title="Add new student"
            visible={isAddStudentModalVisible}
            onOk={this.closeAddStudentModal}
            onCancel={this.closeAddStudentModal}
            width={1000}
          >
            <AddStudentForm
              onSuccess={() => {
                this.closeAddStudentModal();
                this.fetchStudents();
              }}
            />
          </Modal>
          <Footer
            numberOfStudents={students.length}
            handleAddStudentClickEvent={this.openAddStudentModal}
          />
        </Container>
      );
    }
    return <h1>No Students found...</h1>;
  }
}

export default App;
